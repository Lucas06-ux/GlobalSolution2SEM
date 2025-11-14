package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UsuarioBO {
    @Inject
    private UsuarioDAO usuarioDAO;

    public void cadastrar(Usuario usuario) throws SQLException {
        List<Usuario> usuarios = usuarioDAO.listar();
        boolean emailDuplicado = usuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));
        if (emailDuplicado) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail!");
        }
        usuarioDAO.cadastrar(usuario);
    }

    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.listar();
    }

    public Usuario buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        return usuarioDAO.buscar(codigo);
    }

    public void atualizar(Usuario usuario) throws SQLException, EntidadeNaoEncontrada {
        List<Usuario> usuarios = usuarioDAO.listar();
        boolean emailDuplicado = usuarios.stream()
                .anyMatch(u ->
                        u.getEmail().equalsIgnoreCase(usuario.getEmail()) &&
                                u.getCodigo() != usuario.getCodigo()
                );
        if (emailDuplicado) {
            throw new IllegalArgumentException("Este e-mail já está em uso por outro usuário!");
        }
        usuarioDAO.atualizar(usuario);
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        usuarioDAO.remover(codigo);
    }
}
