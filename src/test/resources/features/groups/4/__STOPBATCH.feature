@ALL
@API
@DWP
@B2C
@REGRESSION
@BATCHJOB    
  # I try to start this first, since this can have a 'run out' of 15 minutes.
  # there is a check in the actual tests to verify that the job is actually stopped
Feature: Stop the batchjobs that interfere with our tests

  Background:
    Given I login as API user "soapui_b2c"

  @DISABLE-URD-BATCH-JOB
  Scenario: Disable the URD batchjob
    And the batchjob "UPDATE URD" is set to "Inactive"

