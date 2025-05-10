package tn.mariemby.jee.challenge_stage.tasks.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import tn.mariemby.jee.challenge_stage.users.entities.User;

@Entity
@Table(name = "tasks")
@Data
@JsonIgnoreProperties({"user"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String titre;
    String description;
    Statut statut=Statut.A_FAIRE;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
