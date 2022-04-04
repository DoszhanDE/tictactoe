package de.doszhan.tictactoe.controller;

import de.doszhan.tictactoe.service.UserService;
import de.doszhan.tictactoe.model.User;
import de.doszhan.tictactoe.util.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/auth")
  @JsonView(View.Internal.class)
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestParam(required = true) String username) {
    return this.userService.create(username);
  }

  @GetMapping("/all")
  @JsonView(View.Public.class)
  public List<User> all() {
    return this.userService.all();
  }

}
