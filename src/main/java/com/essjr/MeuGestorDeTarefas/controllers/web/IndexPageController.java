

package com.essjr.MeuGestorDeTarefas.controllers.web;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.mappers.UserMapper;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class IndexPageController {

    private final TaskService taskService;

    public IndexPageController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/")
    public String Home(Model model, @AuthenticationPrincipal User user) {

        UserDTO authenticatedUser = UserMapper.toDTO(user);

        List<TaskDTO> tasks = taskService.findAllByUser(authenticatedUser);

        //    Adiciona um objeto TaskDTO vazio ao modelo para o formulário de criação.
        //    Thymeleaf usará este objeto para "amarrar" os campos do formulário.
        model.addAttribute("newTask", new TaskDTO(null, "", "", null, null, null, null, null));
        model.addAttribute("allPriorities", TaskPriority.values());
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", authenticatedUser);

        return "index";
    }

    @PostMapping("/add-task")
    public String addNewTask(@ModelAttribute("newTask") TaskDTO newTask, @AuthenticationPrincipal User user) {

        UserDTO authenticatedUser = UserMapper.toDTO(user);

        taskService.createTask(newTask, authenticatedUser);

        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/finish")
    public String finishTask(@PathVariable Long id, @AuthenticationPrincipal User user) {
        UserDTO authenticatedUser = UserMapper.toDTO(user);

        taskService.finishTask(id, authenticatedUser);

        return "redirect:/"; // Redireciona para a página inicial
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, @AuthenticationPrincipal User user) {
        UserDTO authenticatedUser = UserMapper.toDTO(user);

        taskService.deleteTask(id, authenticatedUser);

        return "redirect:/"; // Redireciona para a página inicial
    }
}
