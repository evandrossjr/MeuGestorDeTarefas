package com.essjr.MeuGestorDeTarefas.services;


import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.exceptions.ResourceNotFoundException;
import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import com.essjr.MeuGestorDeTarefas.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock // utilizado para criar um objeto falso do TaskRepository
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks // utilizado para criar uma instância real do TaskServiceIMpl injetando os mocks nela
    private TaskServiceImpl taskService;


    private User user;
    private UserDTO userDTO;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
        // o metodo roda antes de cada teste
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Usuário de Teste");
        user.setEmail("teste@email.com");
        user.setRole(UserRole.REGULAR);

        userDTO = new UserDTO(1L, "Usuário de Teste", "teste@email.com", UserRole.REGULAR);

        task = new Task();
        task.setId(10L);
        task.setTitle("Título da Tarefa");
        task.setUser(user);

        taskDTO = new TaskDTO(
                10L, // id
                "Título da Tarefa", // title
                "Descrição da Tarefa", // description
                TaskStatus.TO_DO, // status
                TaskPriority.HIGH, // priority
                null, // createdAt (o serviço vai preencher isso)
                null, // updatedAt (o serviço vai preencher isso)
                null, // finishedAt (o serviço vai preencher isso)
                "1" // userId
        );
    }

    @Test
    @DisplayName("Deve criar uma tarefa com sucesso quando o usuário existe")
    void createTask_withValidUser_shouldSucceed() {
        // Cenário (Given)
        // 1. Quando o userRepository.findById for chamado com o ID 1, retorne nosso usuário de teste.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // 2. Quando o taskRepository.save for chamado, retorne nossa tarefa de teste.
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Ação (When)
        TaskDTO result = taskService.createTask(taskDTO, userDTO);

        // Verificação (Then)
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Título da Tarefa");

        // Verifica se os métodos dos mocks foram chamados
        verify(userRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve encontrar uma tarefa pelo ID quando ela pertence ao usuário")
    void findTaskById_whenTaskBelongsToUser_shouldReturnTaskDTO() {
        // Cenário (Given)
        // Quando o taskRepository.findById for chamado com o ID 10, retorne nossa tarefa.
        // (Lembre-se que nossa 'task' de teste pertence ao 'user' de ID 1)
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        // Ação (When)
        Optional<TaskDTO> result = taskService.findTaskById(10L, userDTO);

        // Verificação (Then)
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(10L);
        assertThat(result.get().title()).isEqualTo("Título da Tarefa");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar encontrar uma tarefa que não pertence ao usuário")
    void findTaskById_whenTaskBelongsToAnotherUser_shouldThrowException() {
        // Cenário (Given)
        UserDTO anotherUserDTO = new UserDTO(2L, "Outro Usuário", "outro@email.com", UserRole.REGULAR);
        // O repositório encontra a tarefa 10, mas ela pertence ao usuário 1.
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        // Ação e Verificação (When & Then)
        // Estamos verificando se uma exceção é lançada quando o "Outro Usuário" (ID 2)
        // tenta buscar a tarefa do "Usuário de Teste" (ID 1).
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.findTaskById(10L, anotherUserDTO);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar uma tarefa para um usuário que não existe")
    void createTask_withInvalidUser_shouldThrowException() {
        // Cenário (Given)
        // Quando o userRepository.findById for chamado, retorne um Optional vazio.
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Ação e Verificação (When & Then)
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createTask(taskDTO, userDTO);
        });

        // Garante que o taskRepository.save NUNCA foi chamado se o usuário não existe.
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa com sucesso quando o usuário é o dono")
    void updateTask_whenUserIsOwner_shouldSucceed() {
        // Cenário (Given)
        TaskDTO updateRequestDTO = new TaskDTO(
                task.getId(),
                "Título Atualizado",
                "Descrição Atualizada",
                TaskStatus.DOING,
                TaskPriority.MEDIUM,
                null, null, null,
                user.getId().toString()
        );

        // Quando o repositório buscar a tarefa, encontre-a.
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        // Quando o repositório salvar a tarefa, apenas retorne a mesma.
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // Ação (When)
        TaskDTO result = taskService.updateTask(task.getId(), updateRequestDTO, userDTO);

        // Verificação (Then)
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Título Atualizado");
        assertThat(result.status()).isEqualTo(TaskStatus.DOING);
        verify(taskRepository, times(1)).findById(task.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar uma tarefa de outro usuário")
    void updateTask_whenUserIsNotOwner_shouldThrowException() {
        // Cenário (Given)
        UserDTO anotherUserDTO = new UserDTO(99L, "Usuário Intruso", "intruso@email.com", UserRole.REGULAR);
        TaskDTO updateRequestDTO = new TaskDTO(null, "Título Malicioso", null, null, null, null, null, null, null);

        // O repositório encontra a tarefa, que pertence ao 'user' de ID 1.
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // Ação e Verificação (When & Then)
        // O usuário de ID 99L tenta atualizar a tarefa do usuário de ID 1.
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateTask(task.getId(), updateRequestDTO, anotherUserDTO);
        });

        // Garante que o método save NUNCA foi chamado.
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve finalizar uma tarefa com sucesso quando ela não está finalizada")
    void finishTask_whenTaskIsNotFinished_shouldSucceed() {
        // Cenário (Given)
        // Garante que a tarefa não está finalizada.
        task.setFinishedAt(null);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ação (When)
        TaskDTO result = taskService.finishTask(task.getId(), userDTO);

        // Verificação (Then)
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(TaskStatus.DONE);
        assertThat(result.finishedAt()).isNotNull();
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar finalizar uma tarefa já finalizada")
    void finishTask_whenTaskIsAlreadyFinished_shouldThrowException() {
        // Cenário (Given)
        // A tarefa já está finalizada.
        task.setFinishedAt(LocalDateTime.now().minusDays(1));
        task.setStatus(TaskStatus.DONE);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // Ação e Verificação (When & Then)
        assertThrows(IllegalStateException.class, () -> {
            taskService.finishTask(task.getId(), userDTO);
        });

        // Garante que o método save NUNCA foi chamado.
        verify(taskRepository, never()).save(any(Task.class));
    }
}



