package br.com.fiap.bo;

import br.com.fiap.dao.MensagemDAO;
import br.com.fiap.dao.ProjetoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Mensagem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MensagemBO {
    @Inject
    private MensagemDAO mensagemDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private ProjetoDAO projetoDAO;

    public void cadastrar(Mensagem mensagem) throws SQLException, EntidadeNaoEncontrada {

        if (mensagem.getConteudo() == null || mensagem.getConteudo().isBlank()) {
            throw new IllegalArgumentException("O conteúdo da mensagem não pode ser vazio!");
        }
        if (mensagem.getDataEnvio() == null) {
            mensagem.setDataEnvio(LocalDate.now());
        }
        usuarioDAO.buscar(mensagem.getIdUsuario());
        projetoDAO.buscar(mensagem.getIdProjeto());
        mensagemDAO.cadastrar(mensagem);
    }

    public List<Mensagem> listar() throws SQLException {
        return mensagemDAO.listar();
    }

    public Mensagem buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return mensagemDAO.buscar(codigo);
    }

    public List<Mensagem> buscarPorUsuario(int codigoUsuario) throws SQLException {
        if (codigoUsuario <= 0)
            throw new IllegalArgumentException("Código do usuário inválido!");

        return mensagemDAO.buscarPorUsuario(codigoUsuario);
    }

    public List<Mensagem> buscarPorProjeto(int codigoProjeto) throws SQLException {
        if (codigoProjeto <= 0)
            throw new IllegalArgumentException("Código do projeto inválido!");

        return mensagemDAO.buscarPorProjeto(codigoProjeto);
    }

    public void atualizar(Mensagem mensagem) throws SQLException, EntidadeNaoEncontrada {

        if (mensagem.getConteudo() == null || mensagem.getConteudo().isBlank()) {
            throw new IllegalArgumentException("O conteúdo da mensagem não pode ser vazio!");
        }
        if (mensagem.getDataEnvio() == null) {
            mensagem.setDataEnvio(LocalDate.now());
        }
        usuarioDAO.buscar(mensagem.getIdUsuario());
        projetoDAO.buscar(mensagem.getIdProjeto());
        mensagemDAO.atualizar(mensagem);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        mensagemDAO.remover(codigo);
    }
}
