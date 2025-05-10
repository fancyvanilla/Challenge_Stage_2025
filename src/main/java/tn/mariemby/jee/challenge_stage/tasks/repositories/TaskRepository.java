package tn.mariemby.jee.challenge_stage.tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.mariemby.jee.challenge_stage.tasks.entities.Statut;
import tn.mariemby.jee.challenge_stage.tasks.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}
