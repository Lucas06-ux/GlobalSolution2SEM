package br.com.fiap.model;

import java.time.LocalDate;

public class Colaboracao {
    private int codigo;
    private LocalDate dataEntrada;
    private String funcao;
    private int idUsuario;
    private int idProjeto;

    public Colaboracao() {}

    public Colaboracao(int codigo, LocalDate dataEntrada, String funcao, int idUsuario, int idProjeto) {
        this.codigo = codigo;
        this.dataEntrada = dataEntrada;
        this.funcao = funcao;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }
}
