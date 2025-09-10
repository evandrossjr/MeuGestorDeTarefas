package com.essjr.MeuGestorDeTarefas.services;

import com.essjr.MeuGestorDeTarefas.dtos.TaskDTO;
import com.essjr.MeuGestorDeTarefas.dtos.UserDTO;
import com.essjr.MeuGestorDeTarefas.exceptions.ResourceNotFoundException;
import com.essjr.MeuGestorDeTarefas.mappers.TaskMapper;
import com.essjr.MeuGestorDeTarefas.models.Task;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import com.essjr.MeuGestorDeTarefas.repositories.TaskRepository;
import com.essjr.MeuGestorDeTarefas.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public TaskDTO createTask(TaskDTO taskDTO, UserDTO userDTO) {

        User user = userRepository.findById(Long.valueOf(taskDTO.userId()))
                .orElseThrow(()-> new ResourceNotFoundException("User not foud with id: " + taskDTO.userId()));

        Task task = TaskMapper.toEntity(taskDTO);

        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return TaskMapper.toDTO(savedTask);
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<TaskDTO> findTaskById(Long id, UserDTO userDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userDTO.id())) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return Optional.of(TaskMapper.toDTO(task));
    }

    @Transactional
    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO, UserDTO userDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(TaskPriority.valueOf(taskDTO.priority()));
        task.setStatus(TaskStatus.valueOf(taskDTO.status()));

        Task updatedTask = taskRepository.save(task);

        return TaskMapper.toDTO(updatedTask);

    }

    @Transactional
    @Override
    public TaskDTO finishTask(Long id, UserDTO userDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userDTO.id())) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        if (task.getFinishedAt() != null) {
            throw new IllegalStateException("Task already finished at: " + task.getFinishedAt());
        }

        task.finish();

        Task finishedTask = taskRepository.save(task);

        return TaskMapper.toDTO(finishedTask);

    }


}
