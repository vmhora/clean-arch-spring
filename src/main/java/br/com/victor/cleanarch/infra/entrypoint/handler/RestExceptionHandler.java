package br.com.victor.cleanarch.infra.entrypoint.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.victor.cleanarch.infra.entrypoint.exception.BadRequestException;
import br.com.victor.cleanarch.infra.entrypoint.exception.BadRequestExceptionDetails;
import br.com.victor.cleanarch.infra.entrypoint.exception.ExceptionDetails;
import br.com.victor.cleanarch.infra.entrypoint.exception.NotFoundException;
import br.com.victor.cleanarch.infra.entrypoint.exception.NotFoundExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundExceptionDetails> handleNotFoundException(NotFoundException nfe) {
        return new ResponseEntity<>(
        		NotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Object not found")
                        .details(nfe.getMessage())
                        .developerMessage(nfe.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
    	var causeException = ex.getCause() != null && ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "Internal server error";
    	var messageException = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title(causeException)
                .details(messageException)
                .developerMessage(ex.getClass().getName())
                .build();
        
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
