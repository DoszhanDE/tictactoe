package de.doszhan.tictactoe.controller;

import de.doszhan.tictactoe.service.ChallengeService;
import de.doszhan.tictactoe.model.Challenge;
import de.doszhan.tictactoe.util.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/challenge")
public class ChallengeController {

  @Autowired
  private ChallengeService challengeService;

  @PostMapping("/create")
  @JsonView(View.Public.class)
  @ResponseStatus(HttpStatus.CREATED)
  public Challenge create(@RequestParam(required = true) Boolean isInviterFirst) {
    return this.challengeService.create(isInviterFirst);
  }

  @GetMapping("/all")
  @JsonView(View.Public.class)
  public List<Challenge> all() {
    return this.challengeService.all();
  }

  @GetMapping("/get")
  @JsonView(View.Public.class)
  public Challenge get(@RequestParam(required = true) UUID challengeId) {
    return this.challengeService.getById(challengeId);
  }

  @PostMapping("/join")
  @JsonView(View.Public.class)
  @ResponseStatus(HttpStatus.OK)
  public Challenge create(@RequestParam(required = true) UUID challengeId) {
    return this.challengeService.join(challengeId);
  }

}
