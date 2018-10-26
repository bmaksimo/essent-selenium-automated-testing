@REGRESSION
Feature: Test scenario for move pay date

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Pay Delay
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Naam" input is "essent"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        
        When Dashboard menu is Billing
        And List option is "ENKEL FACTUREN"
        And Find "Issued" facture and "Betalingsuitstel"
        And Get pay date
        And "Selecteer nieuwe vervaldatum" date is "3 week from now"
        And Changes are confirmed
        Then Payment delayed
