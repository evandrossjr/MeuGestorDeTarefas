package com.essjr.MeuGestorDeTarefas.services;

import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;


    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
