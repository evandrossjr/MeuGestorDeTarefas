package com.essjr.MeuGestorDeTarefas.dtos;

import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO (@NotNull Long id,
                       @NotBlank String name,
                       @NotBlank @Email String email,
                       UserRole role){
}
