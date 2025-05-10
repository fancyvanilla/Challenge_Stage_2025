package tn.mariemby.jee.challenge_stage.tasks.controllers;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.mariemby.jee.challenge_stage.mappers.TaskMapper;
import tn.mariemby.jee.challenge_stage.tasks.dtos.CreateTaskDto;
import tn.mariemby.jee.challenge_stage.tasks.dtos.UpdateTaskDto;
import tn.mariemby.jee.challenge_stage.tasks.entities.Statut;
import tn.mariemby.jee.challenge_stage.tasks.entities.Task;
import tn.mariemby.jee.challenge_stage.tasks.services.TaskService;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.User;
import tn.mariemby.jee.challenge_stage.users.services.UserService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "BearerAuth")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper, UserService userService) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.userService = userService;
    }
    @Operation(
            summary = "Créer une nouvelle tâche",
            description = "Cette endpoint permet de créer une nouvelle tâche pour un utilisateur authentifié."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de la tâche invalides"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
    })

    @PostMapping
    public ResponseEntity<Task> createTask(@AuthenticationPrincipal UserDto dtoUser, @RequestBody CreateTaskDto createTaskDto) {
        User user = userService.getUserByEmail(dtoUser.getEmail());
        Task task=taskMapper.toTask(createTaskDto);
        task.setUser(user);
        return ResponseEntity.ok(taskService.createOrUpdateTask(task));
    }
    @Operation(
            summary = "Récupérer toutes les tâches d'un utilisateur",
            description = "Cette endpoint permet de récupérer toutes les tâches d'un utilisateur authentifié."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des tâches récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
    })

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Task>> getTasksByUser(@AuthenticationPrincipal UserDto dtoUser) {
        User user = userService.getUserByEmail(dtoUser.getEmail());
        return ResponseEntity.ok(taskService.getTasksByUserId(user.getId()));
    }
    @Operation(
            summary = "Récupérer toutes les tâches",
            description = "Cette endpoint permet de récupérer toutes les tâches et nes est accessible qu'aux utilisateurs ayant le rôle ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des tâches récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
    })

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @Operation(
            summary = "Mettre à jour le statut d'une tâche",
            description = "Cette endpoint permet de mettre à jour le statut d'une tâche existante soit 'EN_COURS' soit 'TERMINEE'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Valeur de statut invalide"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @AuthenticationPrincipal UserDto dtoUser, @RequestBody @Valid UpdateTaskDto updateTaskDto) {
        User user = userService.getUserByEmail(dtoUser.getEmail());
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.badRequest().body("Task not found");
        }
        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this task");
        }
        Statut status = Statut.valueOf(updateTaskDto.getStatus());
        if (!task.getStatut().equals(status)){
            task.setStatut(status);
            taskService.createOrUpdateTask(task);
        }
        return ResponseEntity.ok(task);
    }

}
