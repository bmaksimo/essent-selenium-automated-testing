@DWP
@B2C
@BILLING
@CREDIT-AND-CONTROL
Feature: NUAT-5019 Step 8. Billing - Trigger soft dunning process

    @DUNNING
    @NUAT-5019-STEP-8
    Scenario: Reach HB3 dunning level and check if stop access (drop) has initiated
        Given I logged in to DWP as billing.testautomation@essent.be
        #Menu Navigation
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"
        And 1st List element with value at column "Klantnummer & Naam" is checked
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


    @HB1
    Scenario: Reach HB1 dunning level and verify created invoices in DWP
        Given I logged in to DWP as billing.testautomation@essent.be
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at 1st list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at 1st list row

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
        Given Customer with CRM Id parameter:accountNumber is added to dunning whitelist
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row

    @HB2
    Scenario: Reach HB2 dunning level and verify created invoices in DWP
        Given I logged in to DWP as billing.testautomation@essent.be
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at 1st list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at 1st list row

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
        Given Customer with CRM Id parameter:accountNumber is added to dunning whitelist
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row

        # HB2
        Given Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Dashboard menu is Details
        When Sleep for 90 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (ADVANCE) at column ID & Type
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row
        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 2nd row


    @HB3
    Scenario: Reach HB3 dunning level and verify created invoices in DWP
        Given I logged in to DWP as billing.testautomation@essent.be
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at 1st list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at 1st list row

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
        Given Customer with CRM Id parameter:accountNumber is added to dunning whitelist
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Dashboard menu is Details
        And Sleep for 60 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row

        # HB2
        Given Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Dashboard menu is Details
        When Sleep for 90 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (ADVANCE) at column ID & Type
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row
        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 2nd row

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
        When Dashboard menu is Service
        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row
        And Table Interacties contains cell value Outbound document: Dunning SMS HB3 at column Type & Onderwerp on 2nd row
        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 3rd row
        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 4th row
