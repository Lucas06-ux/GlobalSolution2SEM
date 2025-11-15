package br.com.fiap.model;

import java.time.LocalDate;

public class Mensagem {
    int codigo;
    String conteudo;
    LocalDate dataEnvio;
    int idUsuario;
    int idProjeto;

    public Mensagem() {}

    public Mensagem(int codigo, String conteudo, LocalDate dataEnvio, int idUsuario, int idProjeto) {
        this.codigo = codigo;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
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
