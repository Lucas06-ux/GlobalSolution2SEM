package br.com.fiap.dto.tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CadastroTarefaDto {

    @NotBlank(message = "A tarefa deve ter um nome")
    @Size(max = 100)
    String nome;

    @NotBlank(message = "A tarefa deve ter uma descrição")
    @Size(max = 120)
    String descricaoTarefa;

    @NotBlank(message = "É necessário informar a qual área a tarefa está ligada")
    @Size(max = 80)
    String area;

    @NotBlank(message = "é necessário informar o status da tarefa(iniciada, em andamento, encerrada ou ativa)")
    @Size(max = 40)
    String status;


    LocalDate dataCriacao;

    LocalDate dataConclusao;

    @Positive(message = "O código tem que ser maior que 0")
    int codigoProjeto;

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
