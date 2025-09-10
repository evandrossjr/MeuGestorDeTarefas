package com.essjr.MeuGestorDeTarefas.services;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.User;

import java.util.Optional;

public interface TaskService {
    /**
     * Cria uma nova tarefa associada a um usuário.
     * @param taskDTO O objeto da tarefa com os dados a serem salvos.
     * @param userDTO O usuário dono da tarefa.
     * @return A tarefa salva com ID e timestamps.
     */
    TaskDTO createTask(TaskDTO taskDTO, UserDTO userDTO);

    /**
     * Busca uma tarefa pelo seu ID, garantindo que ela pertença ao usuário fornecido.
     * @param id O ID da tarefa a ser buscada.
     * @param userDTO O usuário que está solicitando a tarefa.
     * @return Um Optional contendo a tarefa se encontrada e pertencente ao usuário, caso contrário, um Optional vazio.
     */
    Optional<TaskDTO> findTaskById(Long id, UserDTO userDTO);



    TaskDTO updateTask(Long id, TaskDTO taskDTO, UserDTO userDTO);

    TaskDTO finishTask(Long id, UserDTO userDTO);
}
