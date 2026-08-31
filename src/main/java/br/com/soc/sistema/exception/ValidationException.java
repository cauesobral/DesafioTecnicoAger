package br.com.soc.sistema.exception;

//lembrar de explicar na apresenta
public class ValidationException extends RuntimeException {

	public ValidationException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}

	public ValidationException(String mensagem) {
		super(mensagem);
	}
}