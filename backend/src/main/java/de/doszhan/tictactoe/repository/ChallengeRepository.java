package de.doszhan.tictactoe.repository;

import de.doszhan.tictactoe.model.Challenge;
import de.doszhan.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, java.util.UUID>  {
  List<Challenge> findByInviterIsNotAndInviteeIsNull(User user);
}