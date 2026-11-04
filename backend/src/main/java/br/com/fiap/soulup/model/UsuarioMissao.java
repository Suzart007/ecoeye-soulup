package br.com.fiap.soulup.model;

import br.com.fiap.soulup.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * Participacao de um usuario em uma missao, com o progresso (tabela TB_USUARIOS_MISSOES).
 * Ciclo de vida: PENDENTE -> EM_ANDAMENTO -> CONCLUIDA (100%), ou CANCELADA.
 */
public class UsuarioMissao {

    public static final String PENDENTE = "PENDENTE";
    public static final String EM_ANDAMENTO = "EM_ANDAMENTO";
    public static final String CONCLUIDA = "CONCLUIDA";
    public static final String CANCELADA = "CANCELADA";

    private Long id;
    private Long usuarioId;
    private Long missaoId;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private String status;
    private Integer progresso;

    public UsuarioMissao() {
    }

    public UsuarioMissao(Long id, Long usuarioId, Long missaoId, LocalDate dataInicio, LocalDate dataConclusao,
                         String status, Integer progresso) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.missaoId = missaoId;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.progresso = progresso;
    }

    /** Nova participacao: comeca PENDENTE, com 0% de progresso. */
    public static UsuarioMissao iniciar(Long usuarioId, Long missaoId, LocalDate hoje) {
        return new UsuarioMissao(null, usuarioId, missaoId, hoje, null, PENDENTE, 0);
    }

    /** PENDENTE ou EM_ANDAMENTO: ainda pode receber progresso. */
    @JsonIgnore
    public boolean isAberta() {
        return PENDENTE.equals(status) || EM_ANDAMENTO.equals(status);
    }

    /**
     * Regras do progresso: so muda em missoes abertas, nao pode regredir e,
     * ao chegar em 100%, a missao e CONCLUIDA com a data de hoje (CHECK ck_um_conclusao).
     */
    public void atualizarProgresso(int novoProgresso, LocalDate hoje) {
        if (!isAberta()) {
            throw new RegraNegocioException("A participação " + id + " está " + status + " e não aceita mais progresso.");
        }
        if (novoProgresso < progresso) {
            throw new RegraNegocioException("O progresso não pode regredir: atual " + progresso + "%, informado " + novoProgresso + "%.");
        }
        this.progresso = novoProgresso;
        if (novoProgresso == 100) {
            this.status = CONCLUIDA;
            this.dataConclusao = hoje.isBefore(dataInicio) ? dataInicio : hoje;
        } else if (novoProgresso > 0) {
            this.status = EM_ANDAMENTO;
        }
    }

    public void cancelar() {
        if (!isAberta()) {
            throw new RegraNegocioException("Só é possível cancelar missões PENDENTES ou EM_ANDAMENTO (atual: " + status + ").");
        }
        this.status = CANCELADA;
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

    public Long getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(Long missaoId) {
        this.missaoId = missaoId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }
}
