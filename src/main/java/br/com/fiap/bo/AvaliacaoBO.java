package br.com.fiap.bo;

import br.com.fiap.dao.AvaliacaoDAO;
import br.com.fiap.dao.ProjetoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Avaliacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class AvaliacaoBO {
    @Inject
    private AvaliacaoDAO avaliacaoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private ProjetoDAO projetoDAO;

    public void cadastrar(Avaliacao a) throws SQLException, EntidadeNaoEncontrada {
        if (a.getNota() < 0 || a.getNota() > 10)
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10.");
        if (a.getDataAvaliacao() == null)
            a.setDataAvaliacao(LocalDate.now());
        usuarioDAO.buscar(a.getIdUsuario());
        projetoDAO.buscar(a.getIdProjeto());
        avaliacaoDAO.cadastrar(a);
    }

    public List<Avaliacao> listar() throws SQLException {
        return avaliacaoDAO.listar();
    }

    public Avaliacao buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return avaliacaoDAO.buscar(codigo);
    }

    public List<Avaliacao> buscarPorUsuario(int idUsuario) throws SQLException {
        return avaliacaoDAO.buscarPorUsuario(idUsuario);
    }

    public List<Avaliacao> buscarPorProjeto(int idProjeto) throws SQLException {
        return avaliacaoDAO.buscarPorProjeto(idProjeto);
    }

    public void atualizar(Avaliacao a) throws SQLException, EntidadeNaoEncontrada {
        if (a.getNota() < 0 || a.getNota() > 10)
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10.");
        usuarioDAO.buscar(a.getIdUsuario());
        projetoDAO.buscar(a.getIdProjeto());
        avaliacaoDAO.atualizar(a);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        avaliacaoDAO.remover(codigo);
    }
}
