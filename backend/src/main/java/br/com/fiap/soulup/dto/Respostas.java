package br.com.fiap.soulup.dto;

import br.com.fiap.soulup.model.LogSeguranca;
import br.com.fiap.soulup.model.NivelEngajamento;
import br.com.fiap.soulup.model.SugestaoAvatar;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioMissao;

/** Respostas (JSON) que combinam mais de uma entidade ou trazem valores calculados. */
public final class Respostas {

    private Respostas() {
    }

    /** GET /usuarios/{id}/perfil */
    public record PerfilUsuario(Usuario usuario, int pontos, NivelEngajamento nivel, Integer pontosParaProximoNivel,
                                int missoesConcluidas, int missoesEmAndamento, int sugestoesAguardando) {
    }

    /** GET /ranking */
    public record ItemRanking(int posicao, Long usuarioId, String nome, int missoesConcluidas, int pontos,
                              NivelEngajamento nivel) {
    }

    /** PUT /sugestoes/{id}/aceitar: a sugestao aceita e a participacao criada na missao sugerida. */
    public record RespostaSugestao(SugestaoAvatar sugestao, UsuarioMissao participacao) {
    }

    /** PUT /participacoes/{id}/progresso: a participacao e os pontos ganhos quando a missao e concluida. */
    public record ProgressoAtualizado(UsuarioMissao participacao, int pontosGanhos, int pontosTotais,
                                      NivelEngajamento nivel) {
    }

    /** POST /logs: o log gravado e se a conta foi bloqueada por excesso de falhas. */
    public record RegistroLog(LogSeguranca log, boolean usuarioBloqueado) {
    }
}
