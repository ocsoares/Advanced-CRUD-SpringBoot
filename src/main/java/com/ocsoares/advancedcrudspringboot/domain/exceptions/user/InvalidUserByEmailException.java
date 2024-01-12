package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class InvalidUserByEmailException extends Exception {
    public InvalidUserByEmailException() {
        super("The user with the provided email does not exist");
    }
}
