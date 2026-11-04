package br.com.fiap.soulup.model;

import java.time.LocalDateTime;

/** Mensagem trocada entre o usuario e o avatar (tabela TB_INTERACOES). */
public class Interacao {

    public static final String CONVERSA = "CONVERSA";
    public static final String SUGESTAO = "SUGESTAO";
    public static final String MISSAO = "MISSAO";
    public static final String SUPORTE = "SUPORTE";

    private Long id;
    private Long usuarioAvatarId;
    private LocalDateTime dataInteracao;
    private String mensagemUsuario;
    private String respostaAvatar;
    private String tipo;

    public Interacao() {
    }

    public Interacao(Long id, Long usuarioAvatarId, LocalDateTime dataInteracao, String mensagemUsuario,
                     String respostaAvatar, String tipo) {
        this.id = id;
        this.usuarioAvatarId = usuarioAvatarId;
        this.dataInteracao = dataInteracao;
        this.mensagemUsuario = mensagemUsuario;
        this.respostaAvatar = respostaAvatar;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioAvatarId() {
        return usuarioAvatarId;
    }

    public void setUsuarioAvatarId(Long usuarioAvatarId) {
        this.usuarioAvatarId = usuarioAvatarId;
    }

    public LocalDateTime getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDateTime dataInteracao) {
        this.dataInteracao = dataInteracao;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }

    public void setMensagemUsuario(String mensagemUsuario) {
        this.mensagemUsuario = mensagemUsuario;
    }

    public String getRespostaAvatar() {
        return respostaAvatar;
    }

    public void setRespostaAvatar(String respostaAvatar) {
        this.respostaAvatar = respostaAvatar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
