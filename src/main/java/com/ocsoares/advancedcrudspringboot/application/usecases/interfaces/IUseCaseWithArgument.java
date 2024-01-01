package com.ocsoares.advancedcrudspringboot.application.usecases.interfaces;

public interface IUseCaseWithArgument<R, P> {
    R execute(P parameter);
}
