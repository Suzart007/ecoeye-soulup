package br.com.fiap.soulup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** Usuario da plataforma SoulUp (tabela TB_USUARIOS). */
public class Usuario {

    public static final String ATIVO = "ATIVO";
    public static final String INATIVO = "INATIVO";
    public static final String BLOQUEADO = "BLOQUEADO";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    /** Opcional no cadastro: se nao for enviada, a API usa a data de hoje. */
    private LocalDate dataCadastro;

    @Pattern(regexp = "ATIVO|INATIVO|BLOQUEADO", message = "A situação deve ser ATIVO, INATIVO ou BLOQUEADO.")
    private String situacao;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, LocalDate dataCadastro, String situacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.situacao = situacao;
    }

    /** Regra de negocio 10: so usuarios ATIVOS interagem e recebem sugestoes do avatar. */
    @JsonIgnore
    public boolean isAtivo() {
        return ATIVO.equals(situacao);
    }

    /** Primeiro nome, usado pelo avatar para falar com o usuario. */
    @JsonIgnore
    public String getPrimeiroNome() {
        return nome == null ? "" : nome.trim().split("\\s+")[0];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
