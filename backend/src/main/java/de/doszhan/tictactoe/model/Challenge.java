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
public class Challenge {
  @Id
  @Column(name = "id", columnDefinition = "uuid")
  @JsonView(View.Public.class)
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "inviter_id", referencedColumnName = "id")
  @JsonView(View.Public.class)
  private User inviter;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "invitee_id", referencedColumnName = "id")
  @JsonView(View.Public.class)
  private User invitee;

  @Column(name = "isInviterFirst")
  @JsonView(View.Public.class)
  private Boolean isInviterFirst;

  public static enum Status { pending, in_progress, finished };
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  @JsonView(View.Public.class)
  private Status status = Status.pending;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "winner_id", referencedColumnName = "id")
  @JsonView(View.Public.class)
  private User winner;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created")
  @JsonView(View.Public.class)
  private Date created;
}
