#!/usr/bin/env bash
log=errors_mac.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=UAT06 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver \"-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing\" \"-Dcucumber.options=--tags @ONBOARDING-EXTERNAL\"  -Dwebdriver.chrome.headless=headless"
for i in `seq 1 18`;
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
