package br.com.fiap.dto.avaliacao;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AtualizarAvaliacaoDto {
    @NotNull
    @DecimalMin(value = "0.0", message = "A nota mínima é 0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10")
    private double nota;

    @NotBlank
    @Size(max = 150, message = "O comentário pode ter no máximo 150 caracteres")
    private String comentario;

    @PastOrPresent(message = "A data não pode ser futura")
    private LocalDate dataAvaliacao;

    @Positive(message = "Usuário inválido")
    private int idUsuario;

    @Positive(message = "Projeto inválido")
    private int idProjeto;

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
