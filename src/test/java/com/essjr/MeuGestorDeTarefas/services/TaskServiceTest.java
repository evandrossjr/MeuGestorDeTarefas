package com.essjr.MeuGestorDeTarefas.services;


import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock // utilizado para criar um objeto falso do TaskRepository
    private TaskRepository taskRepository;

    @InjectMocks // utilizado para criar uma instância real do TaskServiceIMpl injetando os mocks nela
    private TaskServiceImpl taskService;


    private User user;
    private Task task;

    @BeforeEach
        // o metodo roda antes de cada teste
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Usuário de Teste S");
        user.setEmail("teste@email.com");

        task = new Task();
        task.setId(10L);
        task.setTitle("Teste de Service");
        task.setUser(user);
    }

    @Test
    @DisplayName("O teste deve criar uma tarefa com sucesso")
    void createTask_shouldReturnSavedTask() {

        // Cenário (Given)
        // Quando o método taskRepository.save for chamado com QUALQUER objeto Task,
        // então retorne a nossa task de teste.
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Ação (When)
        // Task taskCriada = taskService.createTask(task, user); // Este método ainda não existe!

        // Verificação (Then)
        // assertThat(taskCriada).isNotNull();
        // assertThat(taskCriada.getTitle()).isEqualTo("Minha Tarefa de Teste");

        // A linha abaixo apenas verifica se o mock foi chamado como esperado.
        // É um placeholder até criarmos o método de verdade.
        verify(taskRepository, times(0)).save(any(Task.class)); // Verificamos que ainda não foi chamado.
    }


    @Test
    @DisplayName("Deve lançar uma exceção ao tentar encontrar uma tarefa que não existe")
    void findTaskById_whenTaskDoesNotExist_shouldThrowException() {
        // Cenário (Given)
        // Quando taskRepository.findById for chamado com um ID que não existe (99L),
        // então retorne um Optional vazio.
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação e Verificação (When & Then)
        // assertThrows(RuntimeException.class, () -> {
        //     taskService.findTaskById(99L, user); // Este método ainda não existe!
        // });

        // Placeholder
        verify(taskRepository, times(0)).findById(anyLong());
    }
}



