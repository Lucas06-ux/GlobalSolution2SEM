package br.com.fiap.dto.mensagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CadastroMensagemDto {
    @NotBlank(message = "O conteúdo da mensagem é obrigatório")
    @Size(max = 200)
    private String conteudo;

    @PastOrPresent(message = "A data não pode ser futura")
    private LocalDate dataEnvio;

    @Positive(message = "Usuário inválido")
    private int idUsuario;

    @Positive(message = "Projeto inválido")
    private int idProjeto;

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
