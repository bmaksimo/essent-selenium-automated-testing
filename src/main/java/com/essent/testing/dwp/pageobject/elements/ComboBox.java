package com.essent.testing.dwp.pageobject.elements;

import java.util.Optional;

public interface ComboBox {

  Optional<String> getOption(String title, String label);
}
