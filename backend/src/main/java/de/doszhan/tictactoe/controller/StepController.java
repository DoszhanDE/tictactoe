package de.doszhan.tictactoe.controller;

import de.doszhan.tictactoe.service.StepService;
import de.doszhan.tictactoe.model.Step;
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
@RequestMapping("/step")
public class StepController {

  @Autowired
  private StepService stepService;

  @PostMapping("/make")
  @JsonView(View.Public.class)
  @ResponseStatus(HttpStatus.CREATED)
  public Step make(
    @RequestParam(required = true) UUID challengeId,
    @RequestParam(required = true) int horizontally,
    @RequestParam(required = true) int vertically
  ) {
    return this.stepService.make(challengeId, horizontally, vertically);
  }

  @GetMapping("/allByChallenge")
  @JsonView(View.Public.class)
  public List<Step> all(@RequestParam(required = true) UUID challengeId) {
    return this.stepService.allByChallengeId(challengeId);
  }

  @GetMapping("/isMyTurn")
  @JsonView(View.Public.class)
  public Boolean isMyTurn(@RequestParam(required = true) UUID challengeId) {
    return this.stepService.isMyTurn(challengeId);
  }

}
