package br.com.fiap.model;

import java.time.LocalDate;

public class Tarefa {
    private int codigo;
    private String nome;
    private String descricaoTarefa;
    private String area;
    private String status;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private int codigoProjeto;

    public Tarefa() {
    }

    public Tarefa(int codigo, String nome, String descricaoTarefa, String area, String status,
                  LocalDate dataCriacao, LocalDate dataConclusao, int codigoProjeto) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricaoTarefa = descricaoTarefa;
        this.area = area;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.codigoProjeto = codigoProjeto;
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

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public int getCodigoProjeto() {
        return codigoProjeto;
    }

    public void setCodigoProjeto(int codigoProjeto) {
        this.codigoProjeto = codigoProjeto;
    }
}
