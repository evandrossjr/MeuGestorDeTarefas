package com.essjr.MeuGestorDeTarefas.services;


import com.essjr.MeuGestorDeTarefas.dtos.UserRegistrationDTO;
import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void registerUser(UserRegistrationDTO userDTO, UserRole role) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new IllegalStateException("Email já cadastrado.");
        }

        User newUser = new User();
        newUser.setName(userDTO.name());
        newUser.setEmail(userDTO.email());
        newUser.setPasswordHash(passwordEncoder.encode(userDTO.password()));
        newUser.setRole(role); // Define o papel passado como parâmetro

        userRepository.save(newUser);
    }

}
