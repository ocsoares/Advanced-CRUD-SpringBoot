package com.ocsoares.advancedcrudspringboot.domain.exceptions.user.response;

public record InvalidRequestBodyException(String field, String message) {
}
