package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Projeto;
import br.com.fiap.model.Tarefa;
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
public class TarefaDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Tarefa tarefa) throws SQLException{
        try (Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_ct_tarefa(cd_tarefa, nm_tarefa," +
                    "ds_tarefa, ds_area, ds_status, dt_criacao, dt_conclusao, cd_projeto) " +
                    "values(sq_ct_tarefa.nextval,?,?,?,?,?,?,?)", new String[]{"cd_tarefa"});
            setarParametros(tarefa, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                tarefa.setCodigo(rs.getInt(1));
            }
        }
    }
    public List<Tarefa> listar() throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt= conexao.prepareStatement("select * from t_ct_tarefa");
            ResultSet rs = stmt.executeQuery();
            List<Tarefa> lista = new ArrayList<>();
            while (rs.next()){
                Tarefa tarefa = parseTarefa(rs);
                lista.add(tarefa);
            }
            return lista;
        }
    }

    public Tarefa buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_tarefa where cd_tarefa = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontrada("Tarefa não encontrada, Id não existe!");
            return parseTarefa(rs);
        }
    }

    public List<Tarefa> buscarPorProjeto(int codigoProjeto) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_ct_tarefa where cd_projeto = ?");
            stmt.setInt(1, codigoProjeto);
            ResultSet rs = stmt.executeQuery();
            List<Tarefa> lista = new ArrayList<>();
            while (rs.next()) {
                Tarefa tarefa = parseTarefa(rs);
                lista.add(tarefa);
            }
            return lista;
        }
    }

    public void atualizar(Tarefa tarefa) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_ct_tarefa set nm_tarefa = ?," +
                    "ds_tarefa = ?, ds_area = ?, ds_status = ?, dt_criacao = ?, dt_conclusao = ?, cd_projeto = ? where cd_tarefa = ?");
            setarParametros(tarefa, stmt);
            stmt.setInt(8, tarefa.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhuma tarefa encontrado para atualizar!");
        }
    }

    public void remover (int codigo) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_ct_tarefa where cd_tarefa = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. A tarefa não existe!");
        }
    }

    private static void setarParametros(Tarefa tarefa, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, tarefa.getNome());
        stmt.setString(2, tarefa.getDescricaoTarefa());
        stmt.setString(3, tarefa.getArea());
        stmt.setString(4, tarefa.getStatus());
        stmt.setObject(5, tarefa.getDataCriacao());
        stmt.setObject(6, tarefa.getDataConclusao());
        stmt.setInt(7, tarefa.getCodigoProjeto());
    }

    private static Tarefa parseTarefa(ResultSet result) throws SQLException{
        int codigo = result.getInt("cd_tarefa");
        String nome = result.getString("nm_tarefa");
        String descricaoTarefa = result.getString("ds_tarefa");
        String area = result.getString("ds_area");
        String status = result.getString("ds_status");
        LocalDate dataCriacao = result.getDate("dt_criacao").toLocalDate();
        java.sql.Date conclusao = result.getDate("dt_conclusao");
        LocalDate dataConclusao = (conclusao != null) ? conclusao.toLocalDate() : null;
        int codigoProjeto = result.getInt("cd_projeto");
        return new Tarefa(codigo, nome, descricaoTarefa, area, status, dataCriacao, dataConclusao, codigoProjeto);
    }
}
