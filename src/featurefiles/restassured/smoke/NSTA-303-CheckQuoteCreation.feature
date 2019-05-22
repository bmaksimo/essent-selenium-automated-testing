@API
@QUOTE
@SMOKE
@NSTA-303
Feature: Check Quote creation flow

Scenario: Check basic Quote creation flow B2C
	Given I login to iWelcome as "soapui_b2c"
	And "Create_Quote" flow is started
	When Data is prepared for Create qoute request for "prospect"
	And New tc1_quote is created
	Then Quote status is "ACCEPTED"
	And Quoteline exists
	And Quoteline status is "Sent to customer"

	When Simulation that customer signature is recieved
	Then Quote stage status is "SIGNATURE RECEIVED"
	And Quoteline status is "Signature received"

	When File is uploaded as scanned signature
	Then Signin is confirmed
	And Contract is created
	And Contracted EAN exists on account

	When Payment detials are recieved
	Then Wait until contract instance starts
    And Check order in jbilling

    Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
    When Left menu is "sales-marketing"
    And Top menu item is "Klanten"
    And Top action is "Filters"
    And "Klantnummer" input is "parameter:accountNumber"
    And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
    When Dashboard menu is "Contracten"
    Then View list header is "Actieve en toekomstige connecties"
    And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

