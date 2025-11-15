package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Mensagem;
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
public class MensagemDAO {
    @Inject
    private DataSource dataSource;

    public void cadastrar(Mensagem mensagem) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement(
                    "insert into t_ct_mensagem(cd_mensagem, ds_conteudo, dt_envio, cd_usuario, cd_projeto) " +
                            "values(sq_ct_mensagem.nextval, ?, ?, ?, ?)",
                    new String[]{"cd_mensagem"});
            setarParametros(mensagem, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                mensagem.setCodigo(rs.getInt(1));
        }
    }

    public List<Mensagem> listar() throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_mensagem");
            ResultSet rs = stmt.executeQuery();
            List<Mensagem> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseMensagem(rs));
            }
            return lista;
        }
    }

    public Mensagem buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_mensagem where cd_mensagem = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Mensagem não encontrada, Id não existe!");
            return parseMensagem(rs);
        }
    }

    public List<Mensagem> buscarPorUsuario(int codigoUsuario) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_mensagem where cd_usuario = ?");
            stmt.setInt(1, codigoUsuario);
            ResultSet rs = stmt.executeQuery();
            List<Mensagem> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseMensagem(rs));
            }
            return lista;
        }
    }

    public List<Mensagem> buscarPorProjeto(int codigoProjeto) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_mensagem where cd_projeto = ?");
            stmt.setInt(1, codigoProjeto);
            ResultSet rs = stmt.executeQuery();
            List<Mensagem> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseMensagem(rs));
            }
            return lista;
        }
    }

    public void atualizar(Mensagem mensagem) throws SQLException, EntidadeNaoEncontrada {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("update t_ct_mensagem set ds_conteudo = ?, dt_envio = ?, cd_usuario = ?, cd_projeto = ? " +
                            "where cd_mensagem = ?");
            setarParametros(mensagem, stmt);
            stmt.setInt(5, mensagem.getCodigo());
            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhuma mensagem encontrada para atualizar!");
        }
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("delete from t_ct_mensagem where cd_mensagem = ?");
            stmt.setInt(1, codigo);

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Não foi possível remover. A mensagem não existe!");
        }
    }

    private void setarParametros(Mensagem mensagem, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, mensagem.getConteudo());
        stmt.setObject(2, mensagem.getDataEnvio());
        stmt.setInt(3, mensagem.getIdUsuario());
        stmt.setInt(4, mensagem.getIdProjeto());
    }

    private Mensagem parseMensagem(ResultSet rs) throws SQLException {
        int codigo = rs.getInt("cd_mensagem");
        String conteudo = rs.getString("ds_conteudo");
        LocalDate dataEnvio = rs.getDate("dt_envio").toLocalDate();
        int idUsuario = rs.getInt("cd_usuario");
        int idProjeto = rs.getInt("cd_projeto");

        return new Mensagem(codigo, conteudo, dataEnvio, idUsuario, idProjeto);
    }
}
