package com.essjr.MeuGestorDeTarefas.controllers;

import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
