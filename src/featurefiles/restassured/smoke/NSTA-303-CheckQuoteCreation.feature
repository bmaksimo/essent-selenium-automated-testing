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
#	Then Quote status is "Signature received - Accepted"
#	And Quoteline status is "Sent to customer"
#
#	When File is uploaded as scanned signature
#	Then Signin is confirmed
#	And Contract is created
#	And Contracted EAN exists on account
#
#	When Quote details are recieved
#	And Payment detials are recieved
#	Then Wait until contract instance starts
#	And Check if end time is valid
