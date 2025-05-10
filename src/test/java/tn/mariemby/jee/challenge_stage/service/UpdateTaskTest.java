package tn.mariemby.jee.challenge_stage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.mariemby.jee.challenge_stage.tasks.entities.Statut;
import tn.mariemby.jee.challenge_stage.tasks.entities.Task;
import tn.mariemby.jee.challenge_stage.tasks.repositories.TaskRepository;
import tn.mariemby.jee.challenge_stage.tasks.services.TaskServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitre("Test Task");
        task.setDescription("Test Description");
    }

    @Test
    void mettre_a_jour_le_statut_doit_mettre_a_jour_la_tache() {
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        task.setStatut(Statut.valueOf("EN_COURS"));
        Task updatedTask= taskService.createOrUpdateTask(task);
        assertEquals("EN_COURS", updatedTask.getStatut().name());
        verify(taskRepository).save(task);
    }
}
