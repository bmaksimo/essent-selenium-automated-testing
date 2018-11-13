@DWP
@OV
Feature: Check if payment is reconciled

    Background:
        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Check reconciled payment
        When Left menu is contracting-switching
        And Top menu item is Klanten
        And Top action is Filters
        And Label input for "Naam" is "ISE REUS"
        Then View list header is "Klanten"

        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Billing
        Then Transacties list is not empty

        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"
