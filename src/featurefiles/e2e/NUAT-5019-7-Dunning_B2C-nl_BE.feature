@BILLING
@DUNNING
@SMOKE
@E2E

Feature: Billing - Dunning

    Background:
        Given B2C TC1 Active Contract uses "FAKE" address and switch type is "MOVE IN"
        And I logged in to DWP as billing.testautomation@essent.be

    @INACTIVE_CUSTOMER
    Scenario: Reach HB3 dunning level and check if stop access (drop) has initiated
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then View list header is "Klanten"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at 1st list row

        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:billingCustomerId"
        And "Procesdatum" date is "1 month from now"
        Then Invoice run is scheduled

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (ADVANCE) at column ID & Type

        #Go through HB1-3 levels
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        When Dashboard menu is Marktberichten
        Then 1st list element has cell value INITIATE STOP ACCESS at column Module & Label



