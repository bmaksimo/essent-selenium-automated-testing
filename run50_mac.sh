#!/usr/bin/env bash
log=errors_mac.log
directory="mvn clean test -Dtest=E2EEssentRunnerTest -Denvironment=UAT06 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver \"-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing\" \"-Dcucumber.options=--tags @NUAT-5021\"  -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1920x1080"
for i in `seq 1 5`;
do
   #eval ${directory//\//\\/}
   eval ${directory}
   if [ $? -eq 0 ]
  then
    echo "+" >> "${log}"
  else
    echo "-" >> "${log}"
fi
done
