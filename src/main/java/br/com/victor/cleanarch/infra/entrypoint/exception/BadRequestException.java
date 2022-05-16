package br.com.victor.cleanarch.infra.entrypoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
  
	private static final long serialVersionUID = 3511403945515654143L;

	public BadRequestException(String message) {
        super(message);
    }
}
