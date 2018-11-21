#!/usr/bin/env bash
log=errors.log
directory="mvn clean test -Dtest=E2EEssentRunnerTest -Dwebdriver.chrome.driver=C:/Users/dmitr/scoop/apps/chromedriver/current/chromedriver.exe 
\"-Dchrome.user.data.path=C:/Users/dmitr/development/essent-be/Chrome/Profiles/DWP Testing\" -Denvironment=UAT06 \"-Dcucumber.options=--tags @ONBOARDING,@INVOICE-RUN-VF\" 
-DreuseForks=false 
-Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800"
for i in `seq 1 10`;
do
   eval ${directory//\//\\/}
   if [ $? -eq 0 ]
  then
    echo "+" >> "${log}"
  else
    echo "-" >> "${log}"
fi
done
