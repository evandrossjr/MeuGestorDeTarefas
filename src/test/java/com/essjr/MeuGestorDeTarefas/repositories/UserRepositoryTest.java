package com.essjr.MeuGestorDeTarefas.repositories;

import com.essjr.MeuGestorDeTarefas.models.User;
import com.essjr.MeuGestorDeTarefas.models.enuns.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve salvar um usuário com sucesso e retornar pelo ID")
    void SaveUserAndFindById() {
        User user = new User();
        user.setName("Usuário Teste");
        user.setEmail("teste@email.com");
        user.setPasswordHash("hash123");
        user.setRole(UserRole.REGULAR);


        User userSaved = userRepository.save(user);
        Optional<User> userFindedOpt = userRepository.findById(userSaved.getId());

        assertThat(userFindedOpt).isPresent();

        User userFinded = userFindedOpt.get();
        assertThat(userFinded.getId()).isNotNull();
        assertThat(userFinded.getName()).isEqualTo("Usuário Teste");
        assertThat(userFinded.getEmail()).isEqualTo("teste@email.com");
        assertThat(userFinded.getPasswordHash()).isEqualTo("hash123");
        assertThat(userFinded.getRole()).isEqualTo(UserRole.REGULAR);

    }

}