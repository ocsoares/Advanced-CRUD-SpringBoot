package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class UserAlreadyExistsByEmailException extends Exception {
    public UserAlreadyExistsByEmailException() {
        super("There is already a user registered with this email !");
    }
}
