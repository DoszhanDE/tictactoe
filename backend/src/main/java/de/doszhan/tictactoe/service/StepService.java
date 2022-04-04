package de.doszhan.tictactoe.service;

import de.doszhan.tictactoe.model.Challenge;
import de.doszhan.tictactoe.model.Challenge.Status;
import de.doszhan.tictactoe.model.Step;
import de.doszhan.tictactoe.model.User;
import de.doszhan.tictactoe.repository.ChallengeRepository;
import de.doszhan.tictactoe.repository.StepRepository;
import de.doszhan.tictactoe.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class StepService {

  @Autowired
  private StepRepository stepRepository;

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private UserService userService;

  private int stepToCellNumber(Step step) {
    return step.getVertically() * 3 + step.getHorizontally();
  }

  private void identifyWinner(UUID challengeId) {
    Challenge challenge = challengeRepository.getById(challengeId);
    List<Step> steps = allByChallengeId(challengeId);

    List<Integer> inviterCells = new ArrayList();
    List<Integer> inviteeCells = new ArrayList();

    for (Step step : steps) {
      if (challenge.getInviter() == step.getUser()) {
        inviterCells.add(stepToCellNumber(step));
      } else {
        inviteeCells.add(stepToCellNumber(step));
      }
    }
    
    User winner = null;
    Integer[][] winningLines = {
      {0, 1, 2},
      {3, 4, 5},
      {6, 7, 8},
      {0, 3, 6},
      {1, 4, 7},
      {2, 5, 8},
      {0, 4, 8},
      {2, 4, 6},
    };
    for (Integer[] line : winningLines) {
      if (inviterCells.containsAll(Arrays.asList(line))) winner = challenge.getInviter();
      if (inviteeCells.containsAll(Arrays.asList(line))) winner = challenge.getInvitee();
    }

    if (winner != null) {
      challenge.setWinner(winner);
      challenge.setStatus(Status.finished);
      challengeRepository.save(challenge);
    }
  }

  public Step make(UUID challengeId, int horizontally, int vertically) {
    Challenge challenge = challengeRepository.getById(challengeId);
    Boolean isMyTurn = isMyTurn(challengeId);
    List<Step> thisCoordinateSteps = stepRepository.findByChallengeAndHorizontallyAndVertically(
      challenge,
      horizontally,
      vertically
    );

    if (isMyTurn
      && challenge.getStatus() == Status.in_progress
      && thisCoordinateSteps.size() == 0
    ) {

      Step step = new Step();
      step.setChallenge(challenge);
      step.setUser(userService.getLoggedInUser());
      step.setHorizontally(horizontally);
      step.setVertically(vertically);
      stepRepository.save(step);

      identifyWinner(challengeId);

      return step;
    } else {
      return null;
    }
  }

  public List<Step> allByChallengeId(UUID challengeId) {
    Challenge challenge = challengeRepository.getById(challengeId);

    return stepRepository.findByChallenge(challenge);
  }

  public Boolean isMyTurn(UUID challengeId) {
    Challenge challenge = challengeRepository.getById(challengeId);
    User currentUser = userService.getLoggedInUser();
    List<Step> steps = allByChallengeId(challengeId);

    if (steps.size() == 0) {
      if (
        (challenge.getIsInviterFirst() && challenge.getInviter() == currentUser)
        || (!challenge.getIsInviterFirst() && challenge.getInvitee() == currentUser)
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      Step lastStep = steps.get(steps.size() - 1);
      return (lastStep.getUser() == currentUser) ? false : true;
    }
  }


}
