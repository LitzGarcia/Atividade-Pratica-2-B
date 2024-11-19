package com.example.KanBan.task;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Task")
@Entity(name = "Task")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

    private LocalDate dataCriacao;
    private Status status;
    private Prioridade prioridade;
    private LocalDate dataLimite;


    public Task(Task dadosIncompletos) {
        this.titulo = dadosIncompletos.getTitulo();
        this.descricao = dadosIncompletos.getDescricao();
        this.dataCriacao = LocalDate.now();
        this.status = Status.A_FAZER;
        this.prioridade = dadosIncompletos.getPrioridade();
        this.dataLimite = dadosIncompletos.getDataLimite();
    }
}
