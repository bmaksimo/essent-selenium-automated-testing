#!/usr/bin/env bash
log=errors.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=UAT04 -Dwebdriver.chrome.driver=C:/Users/dmitr/scoop/apps/chromedriver/current/chromedriver.exe \"-Dchrome.user.data.path=C:/Users/dmitr/development/essent-be/Chrome/Profiles/DWP Testing\" \"-Dcucumber.options=--tags @B2B_REGRESSION\" -DreuseForks=false -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800"
for i in `seq 1 50`;
do
   eval ${directory//\//\\/}
   if [ $? -eq 0 ]
  then
    echo "+" >> "${log}"
  else
    echo "-" >> "${log}"
fi
done
