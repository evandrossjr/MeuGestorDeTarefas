package com.essjr.MeuGestorDeTarefas.dtos;

import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(Long id,
                      String title,
                      String description,
                      TaskStatus status,
                      TaskPriority priority,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      LocalDateTime finishedAt) {
}
