package com.ocsoares.advancedcrudspringboot.domain.exceptions.user.response;

public record UserAlreadyExistsByEmailResponse(String message, Integer statusCode) {
}
