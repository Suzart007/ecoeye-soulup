package br.com.fiap.soulup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Avatar inteligente que conversa com o usuario (tabela TB_AVATARES). */
public class Avatar {

    public static final String ATIVO = "ATIVO";
    public static final String INATIVO = "INATIVO";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome do avatar é obrigatório.")
    @Size(max = 100, message = "O nome do avatar deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "A descrição do avatar é obrigatória.")
    @Size(max = 255, message = "A descrição do avatar deve ter no máximo 255 caracteres.")
    private String descricao;

    @NotBlank(message = "A personalidade do avatar é obrigatória.")
    @Size(max = 80, message = "A personalidade deve ter no máximo 80 caracteres.")
    private String personalidade;

    @Pattern(regexp = "ATIVO|INATIVO", message = "A situação do avatar deve ser ATIVO ou INATIVO.")
    private String situacao;

    public Avatar() {
    }

    public Avatar(Long id, String nome, String descricao, String personalidade, String situacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.personalidade = personalidade;
        this.situacao = situacao;
    }

    @JsonIgnore
    public boolean isAtivo() {
        return ATIVO.equals(situacao);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
