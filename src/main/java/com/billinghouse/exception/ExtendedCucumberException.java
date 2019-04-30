package com.billinghouse.exception;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.context.ContextService;

public class ExtendedCucumberException extends cucumber.runtime.CucumberException {

  public ExtendedCucumberException(String message) {
    super(String.format("%s, test parameters: %s", message, parameterProvider()));
  }

  private static ParameterProvider parameterProvider() {
    return ((ParameterProvider) ContextService.getContext().getBean("parameterProvider"))
        .consumingNullValues(true);
  }

  public ExtendedCucumberException(String message, Throwable e) {
    super(String.format("%s, test parameters: %s", message, parameterProvider()), e);
  }

  public ExtendedCucumberException(Throwable e) {
    super(String.format("test parameters: %s", parameterProvider()), e);
  }
}
