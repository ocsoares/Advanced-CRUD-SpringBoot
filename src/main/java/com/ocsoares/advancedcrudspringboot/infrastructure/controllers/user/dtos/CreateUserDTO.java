package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
}
