package com.essjr.MeuGestorDeTarefas.repositories;

import com.essjr.MeuGestorDeTarefas.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
