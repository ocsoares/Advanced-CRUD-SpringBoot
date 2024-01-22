package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(ErrorCreatingJWTException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorCreatingJWTException(
            ErrorCreatingJWTException exception
    ) {
        // Usei "var" aqui porque o Tipo do Retorno OBVIAMENTE vai ser "MessageAndStatusCodeResponse", mas digitar assim
        // ficaria MUITO Extenso sem necessidade!!!
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }

    @ExceptionHandler(ErrorJWTVerificationException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorJWTVerificationException(
            ErrorJWTVerificationException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }

    // ADICIONEI ISSO.....
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<MessageAndStatusCodeResponse> handleUsernameNotFoundException(
//            UsernameNotFoundException exception
//    ) {
//        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bodyResponse);
//    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<MessageAndStatusCodeResponse> handleBadCredentialsException(
//            BadCredentialsException exception
//    ) {
//        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bodyResponse);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageAndStatusCodeResponse> handleGeneralException(Exception exception) {
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            // Tratamento específico para erros do Spring Security
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageAndStatusCodeResponse("Usuário inexistente ou senha inválida",
                            HttpStatus.UNAUTHORIZED.value()
                    ));
        } else {
            // Tratamento genérico para outras exceções
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageAndStatusCodeResponse("An unexpected server error occurred",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ));
        }
    }
}
