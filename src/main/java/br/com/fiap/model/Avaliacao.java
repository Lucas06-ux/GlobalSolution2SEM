package br.com.fiap.model;

import java.time.LocalDate;

public class Avaliacao {
    private int codigo;
    private double nota;
    private String comentario;
    private LocalDate dataAvaliacao;
    private int idUsuario;
    private int idProjeto;

    public Avaliacao() {
    }

    public Avaliacao(int codigo, double nota, String comentario, LocalDate dataAvaliacao, int idUsuario, int idProjeto) {
        this.codigo = codigo;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
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
