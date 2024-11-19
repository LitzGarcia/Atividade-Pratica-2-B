package com.example.KanBan.service;

import com.example.KanBan.repository.TaskRepository;
import com.example.KanBan.task.Prioridade;
import com.example.KanBan.task.Status;
import com.example.KanBan.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Task pegarTaskPorId(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<List<Task>> pegarTodasAsTasks() {

        List<Task> TaskFazer = taskRepository.findByStatus(Status.A_FAZER);
        List<Task> TaskProgresso = taskRepository.findByStatus(Status.FAZENDO);
        List<Task> TaskConcluido = taskRepository.findByStatus(Status.CONCLUIDO);

        List<List<Task>> lista = new ArrayList<>();

        lista.add(TaskFazer);
        lista.add(TaskProgresso);
        lista.add(TaskConcluido);

        lista = ordenarPorPrioridade(lista);
        return lista;
    }

    public List<List<Task>> ordenarPorPrioridade(List<List<Task>> lista) {
        List<List<Task>> listaCompleta = new ArrayList<>();

        for (List<Task> colunaTasks : lista) {
            List<Task> listaPrioAlta = new ArrayList<>();
            List<Task> listaPrioMedia = new ArrayList<>();
            List<Task> listaPrioBaixa = new ArrayList<>();

            for (Task task : colunaTasks) {
                switch (task.getPrioridade()) {
                    case ALTA:
                        listaPrioAlta.add(task);
                        break;
                    case MEDIA:
                        listaPrioMedia.add(task);
                        break;
                    case BAIXA:
                        listaPrioBaixa.add(task);
                        break;
                }
            }

            List<Task> listaOrdenada = new ArrayList<>();
            listaOrdenada.addAll(listaPrioAlta);
            listaOrdenada.addAll(listaPrioMedia);
            listaOrdenada.addAll(listaPrioBaixa);

            listaCompleta.add(listaOrdenada);
        }

        return listaCompleta;
    }


    public Task alterando(Task taskAlterada) {
        Task taskAntiga = taskRepository.findById(taskAlterada.getId()).
                orElseThrow(() -> new RuntimeException("Task não encontrado com o id: " + taskAlterada.getId()));

        Task taskAtualizada = new Task(
                taskAntiga.getId(),
                taskAlterada.getTitulo() != null ? taskAlterada.getTitulo() : taskAntiga.getTitulo(),
                taskAlterada.getDescricao() != null ? taskAlterada.getDescricao() : taskAntiga.getDescricao(),
                taskAntiga.getDataCriacao(),
                taskAlterada.getDataLimite() != null ? taskAlterada.getDataLimite() : taskAntiga.getDataLimite(),
                taskAlterada.getPrioridade() != null ? taskAlterada.getPrioridade() : taskAntiga.getPrioridade(),
                taskAlterada.getStatus() != null ? taskAlterada.getStatus() : taskAntiga.getStatus()
        );

        return taskRepository.save(taskAtualizada);
    }

    public Task moverTask(Long id) {
        Task task = taskRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Task não encontrado com o id: " + id));
        if (task.getStatus().equals(Status.A_FAZER)){
            task.setStatus(Status.FAZENDO);
        }
        else if(task.getStatus().equals(Status.FAZENDO)){
            task.setStatus(Status.CONCLUIDO);
        }
        else if (task.getStatus().equals(Status.CONCLUIDO)){
            new RuntimeException("A Task já está concluida");
        }
        return taskRepository.save(task);

    }
    public List<Task> Datalimitefim(LocalDate Datalimite){
        return taskRepository.findByDataLimite(Datalimite);
    }
    public List<Task> filtrarPrioridade(Prioridade prioridade){
        return taskRepository.findByPrioridade(prioridade);
    }
    public List<Task> filtrarAtrasadas(){
        List<Task> taskNaoFeitas = new ArrayList<>();
        List<Task> taskAtrasada = new ArrayList<>();
        LocalDate agora = LocalDate.now();

        taskNaoFeitas.addAll(taskRepository.findByStatus(Status.A_FAZER));
        taskNaoFeitas.addAll(taskRepository.findByStatus(Status.FAZENDO));

        for(Task task : taskNaoFeitas){
            if (agora.isAfter(task.getDataLimite())){
                taskAtrasada.add(task);
            }
        }
        return taskAtrasada;
    }

}
