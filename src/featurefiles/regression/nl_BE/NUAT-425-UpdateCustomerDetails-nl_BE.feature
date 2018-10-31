@DWP
@REGRESSION
@B2B
@CREDIT-AND-CONTROL
Feature: NUAT-425: Update Customer Details - nl_BE

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Update Customer Details
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds


        When Top action is Filters
        And "Klantnummer" input is "150638828"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Bijwerken details klant"
        And Activate "Aanmaningsstop"
        Then Change is immediately visible in Finance & Legal section that "Aanmaningsstop" is active
