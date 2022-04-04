package de.doszhan.tictactoe.repository;

import de.doszhan.tictactoe.model.Challenge;
import de.doszhan.tictactoe.model.Step;
import de.doszhan.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StepRepository extends JpaRepository<Step, java.util.UUID>  {
  List<Step> findByChallenge(Challenge challenge);
  List<Step> findByChallengeAndHorizontallyAndVertically(
    Challenge challenge,
    int horizontally,
    int vertically
  );
}