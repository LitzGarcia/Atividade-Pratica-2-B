package com.example.KanBan.task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "task")
@Entity(name = "task")

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
    private LocalDate dataLimite;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Task(Task dadosIncompletos) {
        this.titulo = dadosIncompletos.getTitulo();
        this.descricao = dadosIncompletos.getDescricao();
        this.dataCriacao = LocalDate.now();
        this.status = Status.A_FAZER;
        this.prioridade = dadosIncompletos.getPrioridade();
        this.dataLimite = dadosIncompletos.getDataLimite();
    }

}
