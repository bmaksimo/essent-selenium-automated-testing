package com.essent.testing.selenium.helper.fluent_wait;

import org.openqa.selenium.support.ui.FluentWait;

public class FluentWaitUtil {
  public static final <T> FluentWait<T> createPollingWaiter(
      T testObject, long secondsTimeout, long secondsPollingEvery) {
    return new FluentWait<>(testObject)
        .withTimeout(java.time.Duration.ofSeconds(secondsTimeout))
        .pollingEvery(java.time.Duration.ofSeconds(secondsPollingEvery));
  }

  public static final <T> FluentWait<T> createWaiter(T testObject, long secondsTimeout) {
    return new FluentWait<>(testObject).withTimeout(java.time.Duration.ofSeconds(secondsTimeout));
  }
}
