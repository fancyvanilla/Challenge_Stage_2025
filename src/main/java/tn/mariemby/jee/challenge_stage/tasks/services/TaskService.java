package tn.mariemby.jee.challenge_stage.tasks.services;

import tn.mariemby.jee.challenge_stage.tasks.entities.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createOrUpdateTask(Task task);

    void deleteTask(Long id);

    List<Task> getTasksByUserId(Long userId);
}
