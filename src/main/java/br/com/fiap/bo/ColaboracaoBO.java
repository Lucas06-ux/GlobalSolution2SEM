package br.com.fiap.bo;

import br.com.fiap.dao.ColaboracaoDAO;
import br.com.fiap.dao.ProjetoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Colaboracao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ColaboracaoBO {
    @Inject
    private ColaboracaoDAO colaboracaoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private ProjetoDAO projetoDAO;

    public void cadastrar(Colaboracao c) throws SQLException, EntidadeNaoEncontrada {

        if (c.getFuncao() == null || c.getFuncao().isBlank())
            throw new IllegalArgumentException("A função não pode estar vazia!");
        if (c.getDataEntrada() == null)
            c.setDataEntrada(LocalDate.now());
        usuarioDAO.buscar(c.getIdUsuario());
        projetoDAO.buscar(c.getIdProjeto());
        colaboracaoDAO.cadastrar(c);
    }

    public List<Colaboracao> listar() throws SQLException {
        return colaboracaoDAO.listar();
    }

    public Colaboracao buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return colaboracaoDAO.buscar(codigo);
    }

    public List<Colaboracao> buscarPorUsuario(int idUsuario) throws SQLException {
        return colaboracaoDAO.buscarPorUsuario(idUsuario);
    }

    public List<Colaboracao> buscarPorProjeto(int idProjeto) throws SQLException {
        return colaboracaoDAO.buscarPorProjeto(idProjeto);
    }

    public void atualizar(Colaboracao c) throws SQLException, EntidadeNaoEncontrada {
        if (c.getFuncao() == null || c.getFuncao().isBlank())
            throw new IllegalArgumentException("A função não pode estar vazia!");
        if (c.getDataEntrada() == null)
            c.setDataEntrada(LocalDate.now());
        usuarioDAO.buscar(c.getIdUsuario());
        projetoDAO.buscar(c.getIdProjeto());
        colaboracaoDAO.atualizar(c);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        colaboracaoDAO.remover(codigo);
    }
}
