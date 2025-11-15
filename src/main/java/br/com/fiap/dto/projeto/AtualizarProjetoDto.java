package br.com.fiap.dto.projeto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AtualizarProjetoDto {

    @NotBlank(message = "É necessário informar o conteúdo do projeto!")
    @Size(max = 50)
    private String conteudo;

    private LocalDate dataEntrada;

    @Positive(message = "O código tem que ser maior que 0")
    private int codigoUsuario;

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
