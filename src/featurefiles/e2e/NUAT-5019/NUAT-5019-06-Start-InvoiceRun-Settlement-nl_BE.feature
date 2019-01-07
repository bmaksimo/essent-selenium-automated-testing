@DWP
@CREDIT-AND-CONTROL
@B2C
Feature: NUAT-5019 Step 6. Triggering billrun, produce a settlement invoice

    Background:
        Given I logged in to DWP as "billing.testautomation@essent.be"

    @INVOICE-RUN-SETTLMENT
    @NUAT-5019-STEP-6
    Scenario: Trigger Invoice run process

        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When  Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked

        Given Top arrow button is "Up"
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"

        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type"


