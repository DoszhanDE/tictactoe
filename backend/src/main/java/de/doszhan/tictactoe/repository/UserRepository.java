package de.doszhan.tictactoe.repository;

import de.doszhan.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, java.util.UUID>  {
}