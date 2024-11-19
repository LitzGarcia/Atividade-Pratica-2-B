package com.example.KanBan.service;

import com.example.KanBan.repository.TaskRepository;
import com.example.KanBan.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task criartask(Task task) {
       return taskRepository.save(task);
    }


    public void deletarTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> pegarTodasTask() {
        return taskRepository.findAll();
    }


    public Task pegarTaskPorId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
    }



}
