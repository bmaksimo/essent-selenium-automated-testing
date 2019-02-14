#!/usr/bin/env bash
#example: $ ./run_run.sh -Dwebdriver.chrome.driver=C:/Google/Chrome/chromedriver/chromedriver.exe -Dchrome.user.data.path=\"C:/Google/Chrome/Profiles/DWP Testing\" -Dssh.user=namita.bhatia -Dssh.keypath=C:/Users/vagrant/.ssh/mac/id_rsa -Dsuite.db.password=changeme -Denvironment=UAT08 "-Dcucumber.options=\"--tags @NUAT-480\"" 3
log=errors_mac.log
directory="mvn clean test -Dtest=DwpEssentRunnerTest $1 $2 $3 $4 $5 $6 $7 $8"
for i in `seq 1 $9`;
do
  echo ${directory}
  if [ $? -eq 0 ]
  then
    echo "+" >> "${log}"
  else
    echo "-" >> "${log}"
  fi
done
