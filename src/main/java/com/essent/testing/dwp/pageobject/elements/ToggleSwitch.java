package com.essent.testing.dwp.pageobject.elements;

import stepdefinitions.dwp.tables.plus.SwitchState;

public interface ToggleSwitch extends Button {
    /**
     * @deprecated use {@link #isOn(String)} or {@link #isOn(String, String)}
     * @return <code>true</code>, when the toggle element is switched on, and <code>false</code> otherwise.
     */
    boolean isOn();
    boolean isOn(String label);
    boolean isOn(String card, String label);
    void switchOn(String label);
    void toggle(SwitchState switchState, String label);
    void toggle(SwitchState switchState, String card, String label);
    void switchOn(String card, String label);
    void switchOff(String label);
    void switchOff(String card, String label);

    /**
     * Is doing nothing when the element, located by given parameters,
     * is visible. Throws Selenium exception otherwise.
     * @param card
     * @param label
     * @return
     */
    boolean checkVisibility(String card, String label);
}
