package com.essjr.MeuGestorDeTarefas.repositories;

import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve salvar uma tarefa com sucesso e encontrá-la pelo ID")
    void deveSalvarETarefaEEncontraLaPeloId() {

        User user = new User();
        user.setName("Usuário Teste");
        user.setEmail("teste@email.com");
        user.setPasswordHash("hash123");
        testEntityManager.persist(user);

        Task task = new Task();
        task.setUser(user);
        task.setTitle("Teste de repositório");
        task.setDescription("Validar o método de salvar tarefa e findById do TaskRepository");
        task.setStatus(TaskStatus.TO_DO);
        task.setPriority(TaskPriority.HIGH);
        task.setCreatedAt(LocalDateTime.now());


        Task taskSalva = taskRepository.save(task);
        Optional<Task> taskEncontradaOpt = taskRepository.findById(taskSalva.getId());

        assertThat(taskEncontradaOpt).isPresent();

        Task taskEcontrada = taskEncontradaOpt.get();
        assertThat(taskEcontrada.getId()).isNotNull();
        assertThat(taskEcontrada.getTitle()).isEqualTo("Teste de repositório");
        assertThat(taskEcontrada.getDescription()).isEqualTo("Validar o método de salvar tarefa e findById do TaskRepository");
        assertThat(taskEcontrada.getStatus()).isEqualTo(TaskStatus.TO_DO);
        assertThat(taskEcontrada.getPriority()).isEqualTo(TaskPriority.HIGH);
        assertThat(taskEcontrada.getCreatedAt()).isNotNull();

    }



}