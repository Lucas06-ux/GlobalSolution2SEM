package br.com.fiap.model;

import java.time.LocalDate;

public class Usuario {
    private int codigo;
    private String nome;
    private String email;
    private String pais;
    private String idioma;
    private String tipoUsuario;
    private String habilidade;
    private LocalDate dataCadastro;

    public Usuario() {
    }

    public Usuario(int codigo, String nome, String email, String pais, String idioma, String tipoUsuario, String habilidade, LocalDate dataCadastro) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.pais = pais;
        this.idioma = idioma;
        this.tipoUsuario = tipoUsuario;
        this.habilidade = habilidade;
        this.dataCadastro = dataCadastro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(String habilidade) {
        this.habilidade = habilidade;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
