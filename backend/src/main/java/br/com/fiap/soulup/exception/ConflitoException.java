package br.com.fiap.soulup.exception;

/** A operacao entra em conflito com dados ja gravados, ex.: e-mail repetido (HTTP 409). */
public class ConflitoException extends RuntimeException {

    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}
