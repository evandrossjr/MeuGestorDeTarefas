

package com.essjr.MeuGestorDeTarefas.controllers.web;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserRegistrationDTO;
import com.essjr.MeuGestorDeTarefas.mappers.UserMapper;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import com.essjr.MeuGestorDeTarefas.repositories.UserRepository;
import com.essjr.MeuGestorDeTarefas.services.TaskService;
import com.essjr.MeuGestorDeTarefas.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class IndexPageController {

    private final TaskService taskService;
    private final UserService userService;

    public IndexPageController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
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

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna o nome do arquivo login.html
    }

    @GetMapping("/register")
    public String register(Model model) {
        // Adiciona um objeto User vazio ao modelo para o formulário de registro
        // Isso é necessário para o th:object="${user}" no formulário funcionar.
        model.addAttribute("user", new UserRegistrationDTO("", "", ""));
        return "register"; // Retorna o nome do arquivo register.html
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") UserRegistrationDTO userDTO,
                                      BindingResult bindingResult) {

        // 3. Se houver erros, retorna para a página de registro para exibi-los
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(userDTO, UserRole.REGULAR);
        } catch (IllegalStateException e) {
            // Se o email já existir, adiciona um erro ao formulário
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return "register";
        }

        return "redirect:/login?success";
    }
}
