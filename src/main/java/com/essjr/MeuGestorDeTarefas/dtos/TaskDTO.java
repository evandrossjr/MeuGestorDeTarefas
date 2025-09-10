package com.essjr.MeuGestorDeTarefas.dtos;

import java.time.LocalDateTime;

public record TaskDTO(String title,
                      String description,
                      String status,
                      String priority,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      LocalDateTime finishedAt,
                      String userId) {
}
