package br.com.victor.cleanarch.domain.exception;

public class LoginExistenteException extends RuntimeException {

	private static final long serialVersionUID = -141460851466160133L;
	
	public LoginExistenteException(String message) {
        super(message);
    }

}
