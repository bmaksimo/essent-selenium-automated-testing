package com.essent.testing.dwp.pageobject.impl.elements;

import com.essent.testing.dwp.pageobject.elements.ToggleSwitch;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class ToggleSwitchImpl extends ButtonImpl implements ToggleSwitch {

    private static final Logger logger = Logger.getLogger(ToggleSwitchImpl.class);

    public ToggleSwitchImpl(WebElement element) {
        super(element);
    }

    @Override
    public boolean isOn() {
        logger.info("STEP:");
        String status = element.getAttribute("autotest-value");
        logger.info(" - TOGGLE STATUS: " + status);
        return BooleanUtils.toBoolean(status);
    }
}
