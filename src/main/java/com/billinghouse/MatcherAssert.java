package com.billinghouse;

import com.billinghouse.cucumber.runtime.parameter.ParameterProvider;
import com.essent.testing.context.ContextService;
import org.hamcrest.Matcher;

public class MatcherAssert {
  private MatcherAssert() {}

  public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
    assertThat("", actual, matcher);
  }

  public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
    ParameterProvider parameterProvider =
        ((ParameterProvider) ContextService.getContext().getBean("parameterProvider"))
            .consumingNullValues(true);
    org.hamcrest.MatcherAssert.assertThat(
        String.format("%s, test parameters: %s", reason, parameterProvider.toString()),
        actual,
        matcher);
  }

  public static void assertThat(String reason, boolean assertion) {
    if (!assertion) {
      throw new AssertionError(reason);
    }
  }
}
