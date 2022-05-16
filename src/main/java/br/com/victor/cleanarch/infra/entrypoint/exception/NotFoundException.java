package br.com.victor.cleanarch.infra.entrypoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1732418306710385142L;

	public NotFoundException(String message) {
        super(message);
    }
}

