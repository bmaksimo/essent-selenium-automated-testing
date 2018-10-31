@BILLING
@DUNNING
@SMOKE
@E2E

Feature: Billing - Dunning

    Background:
        Given B2C TC1 Active Contract uses "FAKE" address and switch type is "MOVE IN"
        And I logged in to DWP as billing.testautomation@essent.be

    @HB1
    Scenario: Reach HB1 dunning level and verify created invoices in DWP
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

        # HB1
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type

    @HB2
    Scenario: Reach HB2 dunning level and verify created invoices in DWP
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

        # HB1
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type

        # HB2
        Given Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Dashboard menu is Details
        When Sleep for 90 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (ADVANCE) at column ID & Type

    @HB3
    Scenario: Reach HB3 dunning level and verify created invoices in DWP
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

        # HB1
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type

        # HB2
        Given Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Dashboard menu is Details
        When Sleep for 90 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (ADVANCE) at column ID & Type

        # HB3
        Given Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Dashboard menu is Details
        When Sleep for 90 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 4th list element has cell value Invoice (ADVANCE) at column ID & Type



