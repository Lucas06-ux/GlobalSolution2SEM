package br.com.fiap.model;

import java.time.LocalDate;

public class Projeto {
    int codigo;
    String conteudo;
    LocalDate dataEntrada;
    int codigoUsuario;

    public Projeto() {
    }

    public Projeto(int codigo, String conteudo, LocalDate dataEntrada, int codigoUsuario) {
        this.codigo = codigo;
        this.conteudo = conteudo;
        this.dataEntrada = dataEntrada;
        this.codigoUsuario = codigoUsuario;
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

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
}
