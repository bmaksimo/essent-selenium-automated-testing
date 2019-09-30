@Ignore
Feature: NUAT-425: Update Customer Details - nl_BE

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-425
    Scenario: Update Customer Details
        When Left menu is "sales-marketing"
        And  Top menu item is "Klanten"

        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Bijwerken details klant"
        And Activate "Aanmaningsstop"
        Then Change is immediately visible in Finance & Legal section that "Aanmaningsstop" is active
