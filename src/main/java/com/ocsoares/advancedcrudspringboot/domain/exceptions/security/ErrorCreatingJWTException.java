package com.ocsoares.advancedcrudspringboot.domain.exceptions.security;

public class ErrorCreatingJWTException extends Exception {
    public ErrorCreatingJWTException() {
        super("An error occurred while creating JWT");
    }
}
