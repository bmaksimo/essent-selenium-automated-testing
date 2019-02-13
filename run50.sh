#!/usr/bin/env bash
log=errors.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest
-Dwebdriver.chrome.driver=\"C:/Users/dmitr/scoop/apps/chromedriver/current/chromedriver.exe\"
-Dchrome.user.data.path=\"C:/Users/dmitr/development/essent-be/Chrome/Profiles/DWP Testing\"
-Denvironment=REG04
-Dcucumber.options=\"--tags @DEDUPLICATE-CUSTOMER\"
-DreuseForks=false
-Dwebdriver.chrome.headless=headless
-Dssh.user=essent_user
-Dssh.keypath=\"/Users/changeme/.ssh/id_rsa\"
-Dsuite.db.password=changeme"
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
