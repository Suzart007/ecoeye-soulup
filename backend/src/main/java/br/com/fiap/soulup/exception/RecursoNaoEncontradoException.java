package br.com.fiap.soulup.exception;

/** O registro procurado nao existe no banco (HTTP 404). */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, long id) {
        super(recurso + " de código " + id + " não encontrado(a).");
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
