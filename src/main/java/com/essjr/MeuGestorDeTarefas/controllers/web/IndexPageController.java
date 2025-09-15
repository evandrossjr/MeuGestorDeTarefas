

package com.essjr.MeuGestorDeTarefas.controllers.web;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class IndexPageController {

    private final TaskService taskService;

    public IndexPageController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/")
    public String Home(Model model) {
        // --- SIMULAÇÃO DE USUÁRIO AUTENTICADO ---
        UserDTO authenticatedUser = new UserDTO(1L, "Mock User", "mock@user.com", UserRole.REGULAR);
        // --- FIM DA SIMULAÇÃO ---

        List<TaskDTO> tasks = taskService.findAllByUser(authenticatedUser);

        // **NOVAS LINHAS**
        // 1. Adiciona um objeto TaskDTO vazio ao modelo para o formulário de criação.
        //    Thymeleaf usará este objeto para "amarrar" os campos do formulário.
        model.addAttribute("newTask", new TaskDTO(null, "", "", null, null, null, null, null));

        model.addAttribute("tasks", tasks);
        model.addAttribute("user", authenticatedUser);

        return "index";
    }

    @PostMapping("/add-task")
    public String addNewTask(@ModelAttribute("newTask") TaskDTO newTask) {
        // --- SIMULAÇÃO DE USUÁRIO AUTENTICADO ---
        UserDTO authenticatedUser = new UserDTO(1L, "Evandro Sacramento", "evandro@email.com",UserRole.REGULAR);
        // --- FIM DA SIMULAÇÃO ---

        taskService.createTask(newTask, authenticatedUser);

        // O padrão Post-Redirect-Get: após submeter os dados, redireciona
        // o usuário para a página inicial para evitar reenvio do formulário.
        return "redirect:/";
    }
}
