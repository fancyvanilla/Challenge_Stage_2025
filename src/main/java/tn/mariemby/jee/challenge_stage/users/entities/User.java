package tn.mariemby.jee.challenge_stage.users.entities;

import jakarta.persistence.*;
import lombok.Data;
import tn.mariemby.jee.challenge_stage.tasks.entities.Task;

import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nom;
    @Column(unique = true)
    String email;
    String mot_de_passe;
    @Enumerated(EnumType.STRING)
    Role role= Role.USER;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
