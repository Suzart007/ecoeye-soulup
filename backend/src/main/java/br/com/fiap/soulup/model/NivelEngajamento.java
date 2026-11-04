package br.com.fiap.soulup.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Nivel do usuario calculado pela soma dos pontos das missoes CONCLUIDAS
 * (cada missao vale de 0 a 100 pontos).
 */
public enum NivelEngajamento {

    INICIANTE("Iniciante", 0),
    ENGAJADO("Engajado", 100),
    GUARDIAO_SUSTENTAVEL("Guardião Sustentável", 250),
    LENDA_SOULUP("Lenda SoulUp", 500);

    private final String descricao;
    private final int pontosMinimos;

    NivelEngajamento(String descricao, int pontosMinimos) {
        this.descricao = descricao;
        this.pontosMinimos = pontosMinimos;
    }

    public static NivelEngajamento dePontos(int pontos) {
        NivelEngajamento nivel = INICIANTE;
        for (NivelEngajamento candidato : values()) {
            if (pontos >= candidato.pontosMinimos) {
                nivel = candidato;
            }
        }
        return nivel;
    }

    /** Quantos pontos faltam para o proximo nivel (null se ja estiver no nivel maximo). */
    public Integer pontosParaProximoNivel(int pontos) {
        int proximo = ordinal() + 1;
        return proximo < values().length ? values()[proximo].pontosMinimos - pontos : null;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }
}
