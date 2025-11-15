package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Projeto;
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
public class ProjetoDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Projeto projeto) throws SQLException{
        try (Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_ct_projeto(cd_projeto, ds_conteudo," +
                    "dt_entrada, cd_usuario) values(sq_ct_projeto.nextval,?,?,?)", new String[]{"cd_projeto"});
            setarParametros(projeto, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                projeto.setCodigo(rs.getInt(1));
            }
        }
    }
    public List<Projeto> listar() throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt= conexao.prepareStatement("select * from t_ct_projeto");
            ResultSet rs = stmt.executeQuery();
            List<Projeto> lista = new ArrayList<>();
            while (rs.next()){
                Projeto projeto = parseProjeto(rs);
                lista.add(projeto);
            }
            return lista;
        }
    }
    public Projeto buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_projeto where cd_projeto = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Projeto não encontrado, Id não existe!");
            return parseProjeto(rs);
        }
    }
    public List<Projeto> buscarPorUsuario(int codigoUsuario) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_projeto where cd_usuario = ?");
            stmt.setInt(1, codigoUsuario);
            ResultSet rs = stmt.executeQuery();
            List<Projeto> lista = new ArrayList<>();
            while (rs.next()) {
                Projeto projeto = parseProjeto(rs);
                lista.add(projeto);
            }
            return lista;
        }
    }
    public void atualizar(Projeto projeto) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_ct_projeto set ds_conteudo = ?," +
                    "dt_entrada = ?, cd_usuario = ? where cd_projeto = ?");
            setarParametros(projeto, stmt);
            stmt.setInt(4, projeto.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum projeto encontrado para atualizar!");
        }
    }

    public void remover (int codigo) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_ct_projeto where cd_projeto = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O projeto não existe!");
        }
    }

    private static void setarParametros(Projeto projeto, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, projeto.getConteudo());
        stmt.setObject(2, projeto.getDataEntrada());
        stmt.setInt(3, projeto.getCodigoUsuario());
    }
    private static Projeto parseProjeto(ResultSet result) throws SQLException{
        int codigo = result.getInt("cd_projeto");
        String conteudo = result.getString("ds_conteudo");
        LocalDate dataEntrada = result.getDate("dt_entrada").toLocalDate();
        int codigoUsuario = result.getInt("cd_usuario");
        return new Projeto(codigo, conteudo, dataEntrada, codigoUsuario);
    }
}
