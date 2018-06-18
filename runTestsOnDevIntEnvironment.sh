#!/usr/bin/env bash

mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=DEVINT01 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver "-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing" -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800 "-Dcucumber.options=--tags @SMOKE"
