#!/usr/bin/env bash
log=errors_mac.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest
-Denvironment=REG04
-Dwebdriver.chrome.driver=/usr/local/bin/chromedriver
-Dchrome.user.data.path=\"/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing\"
-Dcucumber.options=\"--tags @NUAT-5021-01-02\"
-Dwebdriver.chrome.headless=headless
-Dssh.user=essent_user
-Dssh.keypath=\"/Users/changeme/.ssh/id_rsa\"
-Dsuite.db.password=changeme"
for i in `seq 1 18`;
do
   eval ${directory}
   if [ $? -eq 0 ]
  then
    echo "+" >> "${log}"
  else
    echo "-" >> "${log}"
fi
done
