package com.example.KanBan.controller;

import com.example.KanBan.service.TaskService;
import com.example.KanBan.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> criartask(@RequestBody Task dadosIncompletos) {
        return ResponseEntity.ok().body(taskService.criartask(new Task(dadosIncompletos)));

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Task> deletarTask(@PathVariable Long id) {
        taskService.deletarTask(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping
    public ResponseEntity<List<Task>> pegarTodasAsTasks() {
        List<Task> tasks = taskService.pegarTodasTask();
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> pegarTaskPorId(@PathVariable Long id) {
        Task task = taskService.pegarTaskPorId(id);
        return ResponseEntity.ok().body(task);
    }

}

