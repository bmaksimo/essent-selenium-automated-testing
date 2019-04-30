package com.essent.testing.dwp.pageobject.service_contracting;

public interface LogCasePage {

  void setSubjectSelection(String subject);

  void setDescription(String description);

  void setSolution(String solution);

  void setPriority(String priority);

  void save(String buttonText);
}
