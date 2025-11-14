package br.com.fiap.bo;

import br.com.fiap.dao.ProjetoDAO;
import br.com.fiap.dao.TarefaDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Projeto;
import br.com.fiap.model.Tarefa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TarefaBO {

    @Inject
    private TarefaDAO tarefaDAO;

    @Inject
    private ProjetoDAO projetoDAO;


    public void cadastrar(Tarefa tarefa) throws SQLException, EntidadeNaoEncontrada {

        Projeto projeto = projetoDAO.buscar(tarefa.getCodigoProjeto());
        if (projeto == null) {
            throw new EntidadeNaoEncontrada("Projeto informado não existe!");
        }

        if (tarefa.getDataCriacao() != null && tarefa.getDataCriacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de criação não pode ser futura!");
        }
        if (tarefa.getDataCriacao() != null && tarefa.getDataConclusao() != null) {
            if (tarefa.getDataConclusao().isBefore(tarefa.getDataCriacao())) {
                throw new IllegalArgumentException("A data de conclusão não pode ser anterior à data de criação!");
            }
        }
        tarefaDAO.cadastrar(tarefa);
    }

    public List<Tarefa> listar() throws SQLException {
        return tarefaDAO.listar();
    }

    public Tarefa buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return tarefaDAO.buscar(codigo);
    }

    public List<Tarefa> buscarPorProjeto(int codigoProjeto) throws SQLException {
        return tarefaDAO.buscarPorProjeto(codigoProjeto);
    }

    public void atualizar(Tarefa tarefa) throws SQLException, EntidadeNaoEncontrada {
        Projeto projeto = projetoDAO.buscar(tarefa.getCodigoProjeto());
        if (projeto == null) {
            throw new EntidadeNaoEncontrada("Projeto informado não existe!");
        }
        if (tarefa.getDataCriacao() != null && tarefa.getDataCriacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de criação não pode ser futura!");
        }
        if (tarefa.getDataCriacao() != null && tarefa.getDataConclusao() != null) {
            if (tarefa.getDataConclusao().isBefore(tarefa.getDataCriacao())) {
                throw new IllegalArgumentException("A data de conclusão não pode ser anterior à data de criação!");
            }
        }
        tarefaDAO.atualizar(tarefa);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        tarefaDAO.remover(codigo);
    }
}
