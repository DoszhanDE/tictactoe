package de.doszhan.tictactoe.service;

import de.doszhan.tictactoe.model.User;
import de.doszhan.tictactoe.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Value("${jwt.secret}")
  private String jwtSecret;

  private String getJWTToken(String userIdString) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
      .commaSeparatedStringToAuthorityList("ROLE_USER");
   
    String token = Jwts
      .builder()
      .setSubject(userIdString)
      .claim(
        "authorities",
        grantedAuthorities.stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList())
      )
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 3600000 * 10))
      .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
      .compact();

    return token;
  }

  public User create(String username) {
    User user = new User();
    user.setUsername(username);
    userRepository.save(user);

    user.setToken(getJWTToken(user.getId().toString()));
    return userRepository.save(user);
  }

  public List<User> all() {
    return userRepository.findAll();
  }

  public User getLoggedInUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userIdString = (String) auth.getPrincipal();
    User user = userRepository
      .findById(UUID.fromString(userIdString))
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Unable to find a user"
      ));
    return user;
  }

}
