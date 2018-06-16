#!/usr/bin/env bash

mvn clean -Dtest=EssentRunnerTest test -Denvironment=LOCALHOST -Dwebdriver.chrome.driver="$CHROMEDRIVER_PATH" -Dcucumber.options="--tags @CRM,@BILLING,@SERVICEMIX,@BPM,@TG2SC2"
