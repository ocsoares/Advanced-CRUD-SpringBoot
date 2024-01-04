package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class InvalidUserByIdException extends Exception {
    public InvalidUserByIdException() {
        super("The user with the provided ID does not exist");
    }
}
