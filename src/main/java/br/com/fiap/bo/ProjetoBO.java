package br.com.fiap.bo;

import br.com.fiap.dao.ProjetoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Projeto;
import br.com.fiap.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ProjetoBO {
    @Inject
    private ProjetoDAO projetoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    public void cadastrar(Projeto projeto) throws SQLException, EntidadeNaoEncontrada {
        if (projeto.getDataEntrada() != null &&
                projeto.getDataEntrada().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de entrada não pode ser futura!");
        }
        Usuario usuario = usuarioDAO.buscar(projeto.getCodigoUsuario());
        if (usuario == null) {
            throw new EntidadeNaoEncontrada("Usuário responsável pelo projeto não existe!");
        }
        projetoDAO.cadastrar(projeto);
    }


    public List<Projeto> listar() throws SQLException {
        return projetoDAO.listar();
    }


    public Projeto buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return projetoDAO.buscar(codigo);
    }

    public List<Projeto> buscarPorUsuario(int codigoUsuario) throws SQLException {
        return projetoDAO.buscarPorUsuario(codigoUsuario);
    }

    public void atualizar(Projeto projeto) throws SQLException, EntidadeNaoEncontrada {
        if (projeto.getDataEntrada() != null &&
                projeto.getDataEntrada().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de entrada não pode ser futura!");
        }
        Usuario usuario = usuarioDAO.buscar(projeto.getCodigoUsuario());
        if (usuario == null) {
            throw new EntidadeNaoEncontrada("Usuário responsável pelo projeto não existe!");
        }
        projetoDAO.atualizar(projeto);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        projetoDAO.remover(codigo);
    }
}
