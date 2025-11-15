package br.com.fiap.dto.colaboracao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CadastroColaboracaoDto {
    @PastOrPresent(message = "A data não pode ser futura")
    private LocalDate dataEntrada;

    @NotBlank(message = "A função é obrigatória")
    @Size(max = 60)
    private String funcao;

    @Positive(message = "Usuário inválido")
    private int idUsuario;

    @Positive(message = "Projeto inválido")
    private int idProjeto;

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
