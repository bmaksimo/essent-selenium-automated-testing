@SWP
@BILLING
@E2E
@CREDIT-AND-CONTROL
Feature: Billing - Dunning

    Background:
        #Given B2C TC1 Active Contract uses "FAKE" address and switch type is "MOVE IN"
        Given I logged in to DWP as billing.testautomation@essent.be

    @DUNNING
    Scenario: Reach HB3 dunning level and check if stop access (drop) has initiated
        #Menu Navigation
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        #And "Naam" input is "parameter:suitecrm-customer-name"
        And "Naam" input is "beatrijs burg"
        And  1st List element with value at column "Klantnummer & Naam" is checked
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column

        #Go through HB1-3 levels
        Given Dunning day countdown for "parameter:Klantnummer & Naam" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:Klantnummer & Naam" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:Klantnummer & Naam" goes down 28 days
        And Sleep for 90 seconds
        #Navigate to GUI checks
        And Dashboard menu is Marktberichten
        Then 1st list element has cell value INITIATE STOP ACCESS at column Module & Label



