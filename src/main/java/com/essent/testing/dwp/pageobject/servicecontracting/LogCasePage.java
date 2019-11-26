package com.essent.testing.dwp.pageobject.servicecontracting;

public interface LogCasePage {

    void setSubjectSelection(String subject);

    void setDescription(String description);

    void setSolution(String solution);

    void setPriority(String priority);

    void save(String buttonText);

}
