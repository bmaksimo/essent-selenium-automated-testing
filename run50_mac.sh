#!/usr/bin/env bash
log=errors_mac.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=UAT04 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver \"-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing\" \"-Dcucumber.options=--tags @B2B_REGRESSION\"  -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800"
for i in `seq 1 10`;
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
