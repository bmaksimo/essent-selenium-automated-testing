@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-370
Feature: NUAT-370: Create Case With Complaint - nl_BE

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And  Top menu item is "Klanten"

        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds

    @NUAT-370-01
    Scenario: Create case for an account
        When Plus menu is "Service -> Case aanmaken voor de klant"
        And New case for account is created
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        Then Case details are visible

    @NUAT-370-02
    Scenario: Create case in Service dashboard
        When Dashboard menu is "Service"
        And "CASE TOEVOEGEN" is clicked
        And New case for account is created
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        And Case details are visible
