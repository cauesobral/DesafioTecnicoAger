package br.com.soc.sistema.exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }

    public PersistenceException(String mensagem) {
        super(mensagem);
    }
}
