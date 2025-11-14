package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Usuario usuario) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_ct_usuario(cd_usuario, nm_usuario," +
                    "ds_email, ds_pais, ds_idioma, tp_usuario, ds_habilidade, dt_cadastro) values(sq_ct_usuario.nextval, ?,?,?,?,?,?,?)",
                    new String[]{"cd_usuario"});
            setarParametros(usuario, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                usuario.setCodigo(rs.getInt(1));
            }
        }
    }

    public List<Usuario> listar() throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt= conexao.prepareStatement("select * from t_ct_usuario");
            ResultSet rs = stmt.executeQuery();
            List<Usuario> lista = new ArrayList<>();
            while (rs.next()){
                Usuario usuario = parseUsuario(rs);
                lista.add(usuario);
            }
            return lista;
        }
    }

    public Usuario buscar(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_usuario where cd_usuario = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Usuário não encontrado, Id não existe!");
            return parseUsuario(rs);
        }
    }

    public void atualizar(Usuario usuario) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_ct_usuario set nm_usuario = ?," +
                    "ds_email = ?, ds_pais = ?, ds_idioma = ?, tp_usuario = ?, ds_habilidade = ?, dt_cadastro = ? where cd_usuario = ?");
            setarParametros(usuario, stmt);
            stmt.setInt(8, usuario.getCodigo());
            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum usuário encontrado para atualizar!");
        }
    }

    public void remover (int codigo) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_ct_usuario where cd_usuario = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O usuário não existe!");
        }
    }

    private static void setarParametros(Usuario usuario, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getPais());
        stmt.setString(4, usuario.getIdioma());
        stmt.setString(5, usuario.getTipoUsuario());
        stmt.setString(6, usuario.getHabilidade());
        stmt.setObject(7, usuario.getDataCadastro());
    }
    private static Usuario parseUsuario(ResultSet result) throws SQLException{
        int codigo = result.getInt("cd_usuario");
        String nome = result.getString("nm_usuario");
        String email = result.getString("ds_email");
        String pais = result.getString("ds_pais");
        String idioma = result.getString("ds_idioma");
        String tipoUsuario = result.getString("tp_usuario");
        String habilidade = result.getString("ds_habilidade");
        LocalDate dataCadastro = result.getDate("dt_cadastro").toLocalDate();
        return new Usuario(codigo, nome, email, pais, idioma, tipoUsuario, habilidade, dataCadastro);
    }
}
