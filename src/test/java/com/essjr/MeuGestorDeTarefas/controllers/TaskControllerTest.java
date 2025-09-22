package com.essjr.MeuGestorDeTarefas.controllers;

import com.essjr.MeuGestorDeTarefas.config.SecurityConfig;
import com.essjr.MeuGestorDeTarefas.controllers.api.TaskController;
import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class) // 1. Diz ao Spring para testar APENAS o TaskController
@Import(SecurityConfig.class) // Garante que as nossas regras de segurança são carregadas
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc; // 2. O simulador de requisições HTTP

    @MockBean // 3. Cria um mock do TaskService e o coloca no contexto do Spring
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper; // 4. Helper para converter objetos Java em JSON

    private TaskDTO taskDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(1L, "Test User", "test@user.com", UserRole.REGULAR);
        taskDTO = new TaskDTO(10L, "Test API", "Description", TaskStatus.TO_DO, TaskPriority.HIGH, null, null, null);
    }

    @Test
    @DisplayName("Deve criar uma tarefa e retornar status 201 Created")
    @WithMockUser(username = "user@test.com", roles = {"REGULAR"})    void createTask_withValidData_shouldReturnCreated() throws Exception {
        // Cenário (Given)
        // DTO que será enviado no corpo da requisição POST
        TaskDTO requestDTO = new TaskDTO(null, "New Task", "New Desc", TaskStatus.TO_DO, TaskPriority.MEDIUM, null, null, null);

        // Quando o taskService.createTask for chamado, retorne nossa taskDTO de teste.
        when(taskService.createTask(any(TaskDTO.class), any(UserDTO.class))).thenReturn(taskDTO);

        // Ação e Verificação (When & Then)
        mockMvc.perform(post("/api/tasks") // Faz um POST para a URL
                        .contentType(MediaType.APPLICATION_JSON) // Diz que o corpo da requisição é JSON
                        .content(objectMapper.writeValueAsString(requestDTO))) // Converte o DTO em uma string JSON
                .andExpect(status().isCreated()) // Espera que o status HTTP seja 201 (Created)
                .andExpect(jsonPath("$.id").value(10L)) // Usa "jsonPath" para verificar o corpo da resposta JSON
                .andExpect(jsonPath("$.title").value("Test API"));
    }

    @Test
    @DisplayName("Deve buscar uma tarefa pelo ID e retornar status 200 OK")
    @WithMockUser(username = "user@test.com", roles = {"REGULAR"})    void findTaskById_whenTaskExists_shouldReturnOk() throws Exception {
        // Cenário (Given)
        // Quando o taskService.findTaskById for chamado com ID 10 e nosso usuário, retorne a taskDTO.
        when(taskService.findTaskById(eq(10L), any(UserDTO.class))).thenReturn(Optional.of(taskDTO));

        // Ação e Verificação (When & Then)
        mockMvc.perform(get("/api/tasks/{id}", 10L)) // Faz um GET para /api/tasks/10
                .andExpect(status().isOk()) // Espera que o status HTTP seja 200 (OK)
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.title").value("Test API"));
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa e retornar status 200 OK")
    @WithMockUser(username = "user@test.com", roles = {"REGULAR"})    void updateTask_withValidData_shouldReturnOk() throws Exception {
        // Cenário (Given)
        Long taskId = 10L;
        TaskDTO updateRequest = new TaskDTO(null, "Título Atualizado", "Descrição Atualizada", TaskStatus.DOING, TaskPriority.MEDIUM, null, null, null);
        TaskDTO updatedTaskDTO = new TaskDTO(taskId, "Título Atualizado", "Descrição Atualizada", TaskStatus.DOING, TaskPriority.MEDIUM, null, null, null);

        // Quando o serviço de atualização for chamado, retorne o DTO atualizado.
        when(taskService.updateTask(eq(taskId), any(TaskDTO.class), any(UserDTO.class))).thenReturn(updatedTaskDTO);

        // Ação e Verificação (When & Then)
        mockMvc.perform(put("/api/tasks/{id}", taskId) // Faz um PUT para /api/tasks/10
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk()) // Espera 200 OK
                .andExpect(jsonPath("$.title").value("Título Atualizado"))
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    @DisplayName("Deve finalizar uma tarefa e retornar status 200 OK")
    @WithMockUser(username = "user@test.com", roles = {"REGULAR"})    void finishTask_whenTaskExists_shouldReturnOk() throws Exception {
        // Cenário (Given)
        Long taskId = 10L;
        // O DTO retornado pelo serviço terá o status DONE e uma data de finalização.
        TaskDTO finishedTaskDTO = new TaskDTO(taskId, "Test API", "Description", TaskStatus.DONE, TaskPriority.HIGH, null, null, java.time.LocalDateTime.now());

        when(taskService.finishTask(eq(taskId), any(UserDTO.class))).thenReturn(finishedTaskDTO);

        // Ação e Verificação (When & Then)
        // Usamos PATCH pois é uma atualização parcial (apenas o status e datas).
        mockMvc.perform(patch("/api/tasks/{id}/finish", taskId)) // Faz um PATCH para /api/tasks/10/finish
                .andExpect(status().isOk()) // Espera 200 OK
                .andExpect(jsonPath("$.status").value("DONE"))
                .andExpect(jsonPath("$.finishedAt").exists()); // Verifica se o campo finishedAt foi preenchido
    }

}