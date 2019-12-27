package com.billinghouse.cucumber.runtime.scenario;

import com.essent.testing.scenario.RegisteredScenario;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActiveScenarioProvider {

  private static final ActiveScenarioProvider instance = new ActiveScenarioProvider();

  private Map<String, RegisteredScenario> activeScenario =
      Collections.synchronizedMap(new HashMap<>());

  private ActiveScenarioProvider() {}

  public static ActiveScenarioProvider get() {
    return instance;
  };

  public RegisteredScenario getActiveScenario(String name) {
    return activeScenario.get(name);
  }

  public void setActiveScenario(String name, RegisteredScenario activeScenario) {
    this.activeScenario.put(name, activeScenario);
  }
}
