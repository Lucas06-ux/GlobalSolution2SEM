package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Avaliacao;
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
public class AvaliacaoDAO {
    @Inject
    private DataSource dataSource;

    public void cadastrar(Avaliacao a) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("insert into t_ct_avaliacao(cd_avaliacao, nt_avaliacao, ds_comentario, dt_avaliacao, cd_usuario, cd_projeto) " +
                            "values (sq_ct_avaliacao.nextval, ?, ?, ?, ?, ?)",
                    new String[]{"cd_avaliacao"});
            setarParametros(a, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                a.setCodigo(rs.getInt(1));
        }
    }

    public List<Avaliacao> listar() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_avaliacao");
            ResultSet rs = stmt.executeQuery();
            List<Avaliacao> lista = new ArrayList<>();
            while (rs.next()) lista.add(parse(rs));
            return lista;
        }
    }
    public Avaliacao buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "select * from t_ct_avaliacao where cd_avaliacao = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Avaliação não encontrada, Id não existe!");
            return parse(rs);
        }
    }

    public List<Avaliacao> buscarPorUsuario(int idUsuario) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_avaliacao where cd_usuario = ?");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            List<Avaliacao> list = new ArrayList<>();
            while (rs.next()) list.add(parse(rs));
            return list;
        }
    }

    public List<Avaliacao> buscarPorProjeto(int idProjeto) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_avaliacao where cd_projeto = ?");
            stmt.setInt(1, idProjeto);
            ResultSet rs = stmt.executeQuery();
            List<Avaliacao> list = new ArrayList<>();
            while (rs.next()) list.add(parse(rs));
            return list;
        }
    }

    public void atualizar(Avaliacao a) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("update t_ct_avaliacao set nt_avaliacao = ?, ds_comentario = ?, dt_avaliacao = ?, cd_usuario = ?, cd_projeto = ? " +
                            "where cd_avaliacao = ?");
            setarParametros(a, stmt);
            stmt.setInt(6, a.getCodigo());
            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Avaliação não encontrada para atualizar!");
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("delete from t_ct_avaliacao where cd_avaliacao = ?");
            stmt.setInt(1, codigo);
            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Não foi possível remover a avaliação, Id não existe!");
        }
    }

    private void setarParametros(Avaliacao a, PreparedStatement stmt) throws SQLException {
        stmt.setDouble(1, a.getNota());
        stmt.setString(2, a.getComentario());
        stmt.setObject(3, a.getDataAvaliacao());
        stmt.setInt(4, a.getIdUsuario());
        stmt.setInt(5, a.getIdProjeto());
    }

    private Avaliacao parse(ResultSet rs) throws SQLException {
             int codigo =   rs.getInt("cd_avaliacao");
             double nota =   rs.getDouble("nt_avaliacao");
             String comentario =  rs.getString("ds_comentario");
             LocalDate dataAvaliacao = rs.getDate("dt_avaliacao").toLocalDate();
             int idUsuario =   rs.getInt("cd_usuario");
             int idProjeto =  rs.getInt("cd_projeto");
             return new Avaliacao(codigo, nota, comentario, dataAvaliacao, idUsuario, idProjeto);
    }
}
