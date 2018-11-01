@DWP
@REGRESSION
@B2B
@CREDIT-AND-CONTROL
Feature: NUAT-428: Pay Delay - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Pay Delay
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        
        When Dashboard menu is Billing
        And List option is "ENKEL FACTUREN"
        And Find "Issued" facture and "Betalingsuitstel"
        And "Selecteer nieuwe vervaldatum" date is "3 week from now"
        And Changes are confirmed
        Then Payment delayed
