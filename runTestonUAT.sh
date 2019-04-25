#!/usr/bin/env bash

mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=UAT06 -Dwebdriver.chrome.driver=/usr/bin/chromedriver "-Dchrome.user.data.path=/home/bmaksimovic/profiles" "-Dcucumber.options=--tags @SOAP"
