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
        And Plus menu is "Billing -> Start facturatierun"
        Then  Modal dialog is "Start invoicerun"

        When "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"
        Then Invoice run is scheduled

        Given Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type" polling 450 seconds


