@API
@QUOTE
@SMOKE
@NSTA-303
Feature: Check Quote creation flow

Scenario: Check basic Quote creation flow B2C
	Given I login as API user "soapui_b2c"
    And Create active B2C contract with metering "On" and sign date "35 days before now"


