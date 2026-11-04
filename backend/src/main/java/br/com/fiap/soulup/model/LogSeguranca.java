package br.com.fiap.soulup.model;

import java.time.LocalDateTime;

/** Evento de seguranca/auditoria da conta do usuario (tabela TB_LOGS_SEGURANCA). */
public class LogSeguranca {

    public static final String LOGIN_SUCESSO = "LOGIN_SUCESSO";
    public static final String LOGIN_FALHA = "LOGIN_FALHA";
    public static final String BLOQUEIO_CONTA = "BLOQUEIO_CONTA";

    private Long id;
    private Long usuarioId;
    private LocalDateTime dataLog;
    private String tipoEvento;
    private String descricao;
    /** Opcional: acoes automaticas do sistema nao tem IP. */
    private String enderecoIp;

    public LogSeguranca() {
    }

    public LogSeguranca(Long id, Long usuarioId, LocalDateTime dataLog, String tipoEvento, String descricao,
                        String enderecoIp) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.dataLog = dataLog;
        this.tipoEvento = tipoEvento;
        this.descricao = descricao;
        this.enderecoIp = enderecoIp;
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

    public LocalDateTime getDataLog() {
        return dataLog;
    }

    public void setDataLog(LocalDateTime dataLog) {
        this.dataLog = dataLog;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEnderecoIp() {
        return enderecoIp;
    }

    public void setEnderecoIp(String enderecoIp) {
        this.enderecoIp = enderecoIp;
    }
}
