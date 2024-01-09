package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(
                exception.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(bodyResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleNoResourceFoundException(
            NoResourceFoundException exception
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(
                exception.getMessage(), HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyResponse);
    }

    @ExceptionHandler(ErrorCreatingJWTException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorCreatingJWTException(
            ErrorCreatingJWTException exception
    ) {
        // Usei "var" aqui porque o Tipo do Retorno OBVIAMENTE vai ser "MessageAndStatusCodeResponse", mas digitar assim
        // ficaria MUITO Extenso sem necessidade!!!
        var bodyResponse = new MessageAndStatusCodeResponse(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }

    @ExceptionHandler(ErrorJWTVerificationException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorJWTVerificationException(
            ErrorJWTVerificationException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }
}
