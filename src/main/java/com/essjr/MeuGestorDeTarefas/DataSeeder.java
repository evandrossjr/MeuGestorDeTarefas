package com.essjr.MeuGestorDeTarefas;

import com.essjr.MeuGestorDeTarefas.dtos.UserRegistrationDTO;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import com.essjr.MeuGestorDeTarefas.repositories.UserRepository;
import com.essjr.MeuGestorDeTarefas.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final UserRepository userRepository;
    // Usar um logger é uma prática melhor do que System.out.println
    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    public DataSeeder(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Este método será executado quando a aplicação estiver totalmente pronta.
        seedUsers();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            logger.info("Nenhum utilizador encontrado, a criar dados de teste...");

            // Cria utilizador Regular
            UserRegistrationDTO regularUser = new UserRegistrationDTO(
                    "Usuário Padrão",
                    "usuario@email.com",
                    "senha123"
            );
            // Passamos o papel diretamente aqui para o exemplo
            userService.registerUser(regularUser, UserRole.REGULAR);

            // Cria utilizador Admin
            UserRegistrationDTO adminUser = new UserRegistrationDTO(
                    "Administrador",
                    "admin@email.com",
                    "admin122"
            );
            userService.registerUser(adminUser, UserRole.ADMIN);

            logger.info("Utilizadores de teste criados com sucesso!");
        } else {
            logger.info("A base de dados já contém utilizadores. Nenhum dado de teste foi criado.");
        }
    }
}