package com.essjr.MeuGestorDeTarefas.controllers.api;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.exceptions.ResourceNotFoundException;
import com.essjr.MeuGestorDeTarefas.mappers.UserMapper;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @AuthenticationPrincipal(expression = "username") String username) {

        UserDTO authenticatedUser = new UserDTO(1L, username, username, UserRole.REGULAR);

        TaskDTO createdTask = taskService.createTask(taskDTO, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id,@AuthenticationPrincipal(expression = "username") String username) {
        UserDTO authenticatedUser = new UserDTO(1L, username, username, UserRole.REGULAR);


        // O findTaskById retorna um Optional. Usamos .map() e .orElseThrow() para lidar com isso.
        return taskService.findTaskById(id, authenticatedUser)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com a tarefa no corpo.
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id)); // Se não, lança a exceção que resultará em um 404 Not Found.
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO, @AuthenticationPrincipal(expression = "username") String username) {
        UserDTO authenticatedUser = new UserDTO(1L, username, username, UserRole.REGULAR);

        TaskDTO updatedTask = taskService.updateTask(id, taskDTO, authenticatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask); // Retorna 200 OK com a tarefa atualizada
    }

    // Endpoint para FINALIZAR uma tarefa
    @PatchMapping("/{id}/finish")
    public ResponseEntity<TaskDTO> finishTask(@PathVariable Long id, @AuthenticationPrincipal(expression = "username") String username) {
        UserDTO authenticatedUser = new UserDTO(1L, username, username, UserRole.REGULAR);

        TaskDTO finishedTask = taskService.finishTask(id, authenticatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(finishedTask); // Retorna 200 OK com a tarefa finalizada
    }


}
