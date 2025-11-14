package br.com.fiap.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CadastroUsuarioDto {

    @NotBlank(message = "É necessário informar um nome!")
    @Size(max = 80)
    private String nome;

    @NotBlank(message = "É necessário informar um email válido!")
    @Size(max = 60)
    private String email;

    @NotBlank(message = "Informe seu país de origem!")
    @Size(max = 45)
    private String pais;

    @NotBlank(message = "Informe seu idioma falado")
    @Size(max = 60)
    private String idioma;

    @NotBlank(message = "É necessário informar seu tipo de usuario(Profissional, estudante ou empresa)")
    @Size(max = 30)
    private String tipoUsuario;

    @NotBlank(message = "É necessário informar as suas habilidades!")
    @Size(max = 200)
    private String habilidade;

    @PastOrPresent
    private LocalDate dataCadastro;

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
