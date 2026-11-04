package br.com.fiap.soulup.model;

import br.com.fiap.soulup.exception.RegraNegocioException;

import java.time.LocalDateTime;

/** Sugestao feita pelo avatar ao usuario, geralmente de uma missao (tabela TB_SUGESTOES_AVATAR). */
public class SugestaoAvatar {

    public static final String ENVIADA = "ENVIADA";
    public static final String ACEITA = "ACEITA";
    public static final String RECUSADA = "RECUSADA";
    public static final String EXPIRADA = "EXPIRADA";

    private Long id;
    private Long usuarioId;
    private Long avatarId;
    /** Opcional: uma sugestao pode nao estar ligada a uma missao. */
    private Long missaoId;
    private LocalDateTime dataSugestao;
    private String texto;
    private String status;

    public SugestaoAvatar() {
    }

    public SugestaoAvatar(Long id, Long usuarioId, Long avatarId, Long missaoId, LocalDateTime dataSugestao,
                          String texto, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.avatarId = avatarId;
        this.missaoId = missaoId;
        this.dataSugestao = dataSugestao;
        this.texto = texto;
        this.status = status;
    }

    /** So uma sugestao ENVIADA (aguardando resposta) pode ser aceita ou recusada. */
    public void responder(boolean aceitar) {
        if (!ENVIADA.equals(status)) {
            throw new RegraNegocioException("A sugestão " + id + " já foi respondida (status " + status + ").");
        }
        this.status = aceitar ? ACEITA : RECUSADA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public Long getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(Long missaoId) {
        this.missaoId = missaoId;
    }

    public LocalDateTime getDataSugestao() {
        return dataSugestao;
    }

    public void setDataSugestao(LocalDateTime dataSugestao) {
        this.dataSugestao = dataSugestao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
