package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.response.UserAlreadyExistsByEmailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsByEmailException.class)
    public ResponseEntity<UserAlreadyExistsByEmailResponse> handleUserAlreadyExistsByEmailException(
            UserAlreadyExistsByEmailException exception
    ) {
        UserAlreadyExistsByEmailResponse bodyResponse = new UserAlreadyExistsByEmailResponse(
                exception.getMessage(), HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyResponse);
    }
}
