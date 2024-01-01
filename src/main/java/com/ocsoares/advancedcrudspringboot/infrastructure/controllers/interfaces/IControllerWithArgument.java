package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces;

public interface IControllerWithArgument<R, P> {
    R handle(P parameter);
}
