@SOAP
Feature: SOAP2REST:Create B2B quote

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    Scenario: DROP for a Non-residential client

        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 15 seconds

        When Dashboard menu is "Contracten"
        And Find "Actief" contract
        And Dashboard menu is "Marktberichten"
        And Click on "Start nieuw marktbericht"
        And Click Select Contractline
        And Search for ean code
        Then Changes are confirmed

        When Input in "Module" is "INITIATE STOP ACCESS"
        And Input in "Label" is "Drop/Request Budget Meter"
        And Check toggle "Testing"
        And Check toggle "Market mock"
        And Changes are confirmed
        Then Confirm task was "Non-Residential Drop"
