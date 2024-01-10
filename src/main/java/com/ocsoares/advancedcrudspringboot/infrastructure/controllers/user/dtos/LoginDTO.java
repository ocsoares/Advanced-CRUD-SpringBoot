package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "The email is required") @Email(message = "Must be a valid email address") String email,
        @NotBlank(message = "The password is required") String password) {
}
