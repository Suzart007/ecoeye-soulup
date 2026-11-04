package br.com.fiap.soulup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Missao sustentavel que gera pontos ao ser concluida (tabela TB_MISSOES). */
public class Missao {

    public static final String ATIVA = "ATIVA";
    public static final String INATIVA = "INATIVA";
    public static final String ENCERRADA = "ENCERRADA";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O título da missão é obrigatório.")
    @Size(min = 3, max = 120, message = "O título deve ter entre 3 e 120 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição da missão é obrigatória.")
    @Size(min = 10, max = 255, message = "A descrição deve ter entre 10 e 255 caracteres.")
    private String descricao;

    @NotNull(message = "A pontuação é obrigatória.")
    @Min(value = 0, message = "A pontuação mínima é 0.")
    @Max(value = 100, message = "A pontuação máxima é 100.")
    private Integer pontuacao;

    @Pattern(regexp = "ATIVA|INATIVA|ENCERRADA", message = "A situação deve ser ATIVA, INATIVA ou ENCERRADA.")
    private String situacao;

    public Missao() {
    }

    public Missao(Long id, String titulo, String descricao, Integer pontuacao, String situacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
        this.situacao = situacao;
    }

    /** So missoes ATIVAS podem ser iniciadas e sugeridas pelo avatar. */
    @JsonIgnore
    public boolean isAtiva() {
        return ATIVA.equals(situacao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
