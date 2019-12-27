package com.essent.testing.editors;

import com.essent.testing.config.ConnectionType;
import java.beans.PropertyEditorSupport;

public class ConnectionTypeEditor extends PropertyEditorSupport {
  @Override
  public void setAsText(String text) {
    setValue(ConnectionType.fromType(text.toLowerCase()));
  }
}
