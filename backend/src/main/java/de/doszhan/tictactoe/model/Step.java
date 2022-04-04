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
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Getter
@Setter
public class Step {
  @Id
  @Column(name = "id", columnDefinition = "uuid")
  @JsonView(View.Public.class)
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "challenge_id", referencedColumnName = "id")
  @JsonView(View.Public.class)
  private Challenge challenge;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonView(View.Public.class)
  private User user;

  @Column(name = "horizontally")
  @JsonView(View.Public.class)
  private int horizontally;

  @Column(name = "vertically")
  @JsonView(View.Public.class)
  private int vertically;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created")
  @JsonView(View.Public.class)
  private Date created;
}
