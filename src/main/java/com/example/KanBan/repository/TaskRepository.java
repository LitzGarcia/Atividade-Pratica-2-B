package com.example.KanBan.repository;

import com.example.KanBan.task.Prioridade;
import com.example.KanBan.task.Status;
import com.example.KanBan.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByDataLimite(LocalDate dataLimite);
    List<Task> findByPrioridade(Prioridade Prioridade);
}
