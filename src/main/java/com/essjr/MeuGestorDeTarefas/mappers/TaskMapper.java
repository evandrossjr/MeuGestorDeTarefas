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
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
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
        task.setId(taskDTO.id());
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setStatus(TaskStatus.valueOf(String.valueOf(taskDTO.status())));
        task.setPriority(TaskPriority.valueOf(String.valueOf(taskDTO.priority())));
        return task;
    }
}
