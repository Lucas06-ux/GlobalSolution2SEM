package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Colaboracao;
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
public class ColaboracaoDAO {
    @Inject
    private DataSource dataSource;

    public void cadastrar(Colaboracao c) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("insert into t_ct_colaboracao(cd_colaboracao, dt_entrada, ds_funcao, cd_usuario, cd_projeto) " +
                            "values (sq_ct_colaboracao.nextval, ?, ?, ?, ?)",
                    new String[]{"cd_colaboracao"}
            );
            setarParametros(c, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) c.setCodigo(rs.getInt(1));
        }
    }

    public List<Colaboracao> listar() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_colaboracao");
            ResultSet rs = stmt.executeQuery();
            List<Colaboracao> lista = new ArrayList<>();
            while (rs.next()) lista.add(parseColaboracao(rs));
            return lista;
        }
    }

    public Colaboracao buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_colaboracao where cd_colaboracao = ?"
            );
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Colaboração não encontrada, Id não existe!");
            return parseColaboracao(rs);
        }
    }

    public List<Colaboracao> buscarPorUsuario(int idUsuario) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_colaboracao where cd_usuario = ?"
            );
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            List<Colaboracao> lista = new ArrayList<>();

            while (rs.next()) lista.add(parseColaboracao(rs));
            return lista;
        }
    }
    public List<Colaboracao> buscarPorProjeto(int idProjeto) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("select * from t_ct_colaboracao where cd_projeto = ?"
            );
            stmt.setInt(1, idProjeto);
            ResultSet rs = stmt.executeQuery();
            List<Colaboracao> lista = new ArrayList<>();
            while (rs.next()) lista.add(parseColaboracao(rs));
            return lista;
        }
    }
    public void atualizar(Colaboracao c) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("update t_ct_colaboracao set dt_entrada = ?, ds_funcao = ?, cd_usuario = ?, cd_projeto = ? " +
                            "where cd_colaboracao = ?"
            );
            setarParametros(c, stmt);
            stmt.setInt(5, c.getCodigo());
            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhuma colaboração encontrada para atualizar!");
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("delete from t_ct_colaboracao where cd_colaboracao = ?");
            stmt.setInt(1, codigo);
            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Não foi possível remover, a colaboração não existe!");
        }
    }

    private void setarParametros(Colaboracao c, PreparedStatement stmt) throws SQLException {
        stmt.setObject(1, c.getDataEntrada());
        stmt.setString(2, c.getFuncao());
        stmt.setInt(3, c.getIdUsuario());
        stmt.setInt(4, c.getIdProjeto());
    }

    private Colaboracao parseColaboracao(ResultSet rs) throws SQLException {
              int codigo = rs.getInt("cd_colaboracao");
              LocalDate dataEntrada = rs.getDate("dt_entrada").toLocalDate();
              String funcao = rs.getString("ds_funcao");
              int IdUsuario = rs.getInt("cd_usuario");
              int IdProjeto = rs.getInt("cd_projeto");
              return new Colaboracao(codigo, dataEntrada, funcao, IdUsuario, IdProjeto);
    }
}
