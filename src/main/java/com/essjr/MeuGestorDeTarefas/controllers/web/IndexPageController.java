

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


        model.addAttribute("tasks", tasks);
        model.addAttribute("user", authenticatedUser);

        return "index";
    }
}
