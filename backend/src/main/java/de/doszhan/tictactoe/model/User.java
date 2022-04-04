package de.doszhan.tictactoe.model;

import de.doszhan.tictactoe.util.View;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Getter
@Setter
@Table(name="player")
public class User {
  @Id
  @Column(name = "id", columnDefinition = "uuid")
  @JsonView(View.Public.class)
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID id;

  @Column(name = "username")
  @JsonView(View.Public.class)
  private String username;

  @Column(name = "token", length = 511)
  @JsonView(View.Internal.class)
  private String token;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created")
  @JsonView(View.Public.class)
  private Date created;
}
