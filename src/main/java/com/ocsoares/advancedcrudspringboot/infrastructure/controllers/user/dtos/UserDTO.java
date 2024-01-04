package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
}
