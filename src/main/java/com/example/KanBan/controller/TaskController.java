package com.example.KanBan.controller;

import com.example.KanBan.service.TaskService;
import com.example.KanBan.task.Prioridade;
import com.example.KanBan.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<List<Task>>> pegarTodasAsTasks() {
        List<List<Task>> tasks = taskService.pegarTodasAsTasks();
        return ResponseEntity.ok().body(tasks);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> pegarTaskPorId(@PathVariable Long id) {
        Task task = taskService.pegarTaskPorId(id);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping(value = "/Datalimite/{Datalimite}")
    public ResponseEntity<List<Task>> pegandodatalimite(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate Datalimite) {
        return ResponseEntity.ok().body(taskService.Datalimitefim(Datalimite));
    }


    @GetMapping(value = "/Prioridade/{Prioridade}")
    public ResponseEntity<List<Task>> pegandoporprioridade(@PathVariable Prioridade Prioridade){
        return ResponseEntity.ok().body(taskService.filtrarPrioridade(Prioridade));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Task> atualizarTask(@PathVariable Long id,@RequestBody Task dados){
        dados.setId(id);
        return ResponseEntity.ok().body(taskService.alterando(dados));
    }

    @PutMapping(value = "/{id}/move")
    public ResponseEntity<?> moveTask(@PathVariable Long id) {
        try {
            Task taskAtualizada = taskService.moverTask(id);
            return ResponseEntity.ok().body(taskAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/atrasadas")
    public ResponseEntity<List<Task>> pegandoatrasadas(){
        return ResponseEntity.ok().body(taskService.filtrarAtrasadas());
    }

}


