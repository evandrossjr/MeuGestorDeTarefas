package com.essjr.MeuGestorDeTarefas.mappers;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;

public class TaskMapper {

    public static TaskDTO toDTO(Task task){
        if (task == null){
            return null;
        }
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getFinishedAt(),
                task.getUser().getId().toString()
        );
    }

    public static Task toEntity(TaskDTO taskDTO){
        if (taskDTO == null) {
            return null;
        }

        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setStatus(TaskStatus.valueOf(taskDTO.status().toUpperCase()));
        task.setPriority(TaskPriority.valueOf(taskDTO.priority().toUpperCase()));
        return task;
    }
}
