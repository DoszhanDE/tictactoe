package de.doszhan.tictactoe.service;

import de.doszhan.tictactoe.model.Challenge;
import de.doszhan.tictactoe.model.Challenge.Status;
import de.doszhan.tictactoe.repository.ChallengeRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Component
public class ChallengeService {

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private UserService userService;

  public Challenge create(Boolean isInviterFirst) {
    Challenge challenge = new Challenge();
    challenge.setIsInviterFirst(isInviterFirst);
    challenge.setInviter(userService.getLoggedInUser());
    
    return challengeRepository.save(challenge);
  }

  public Challenge join(UUID challengeId) {
    Challenge challenge = this.getById(challengeId);
    challenge.setInvitee(userService.getLoggedInUser());
    challenge.setStatus(Status.in_progress);

    return challengeRepository.save(challenge);
  }

  public List<Challenge> all() {
    return challengeRepository.findByInviterIsNotAndInviteeIsNull(userService.getLoggedInUser());
  }

  public Challenge getById(UUID challengeId) {
    Challenge challenge = challengeRepository
      .findById(challengeId)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Unable to find a challenge"
      ));
    return challenge;
  }


}
