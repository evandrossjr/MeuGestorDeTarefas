package com.essjr.MeuGestorDeTarefas.repositories;

import com.essjr.MeuGestorDeTarefas.models.Task;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllByUserId(Long id);

}
