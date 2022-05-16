package br.com.victor.cleanarch.domain.exception;

public class UsuarioNotFoudException extends RuntimeException {

	private static final long serialVersionUID = 2995296927513422463L;

	public UsuarioNotFoudException(String message) {
        super(message);
    }
}
