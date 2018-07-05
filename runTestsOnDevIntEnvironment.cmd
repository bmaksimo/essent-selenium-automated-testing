#!/usr/bin/env bash

mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=DEVINT01 -Dwebdriver.chrome.driver=C:/Users/dmitr/scoop/apps/chromedriver/current/chromedriver.exe "-Dchrome.user.data.path=C:/Users/dmitr/development/essent-be/Chrome/Profiles/DWP Testing" "-Dcucumber.options=--tags @SMOKE" -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800 -DreuseForks=false
