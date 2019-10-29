@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-433: Log Questions Of Customers - en_BE

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-433
    Scenario: Log questions of customers
        When Left menu is "sales-marketing"
        And  Top menu item is "Klanten"

        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Click on top menu button PLUS and navigate to "Service -> Case aanmaken voor de klant"
        And New case for account is created
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 60 seconds
        And Case details are visible
