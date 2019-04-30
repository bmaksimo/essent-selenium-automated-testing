@DWP
@B2B
@REGRESSION
@JBILLING
@NUAT-510
@UNSTABLE
Feature: NUAT-510 Triggering advance invoice run. Check is invoice created in DWP and jbilling

    @NUAT-510-01
    Scenario: Trigger Invoice run process
        Given I logged in to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        Given B2B Active Contract is
            | productType | isFakeAddress | switchType      | meterType | kwMax |
            | UP          | FAKE          | SUPPLIER SWITCH | YMR       | 50000 |
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is "Start invoicerun"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"

        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds
        And "1st" List element with value at column "ID & Type" is checked

    # JBilling
    @NUAT-510-02
    Scenario: Check invoices and orders in JBilling
        Given I logged in to JBilling as "billing_testautomation"
        When JBilling top menu item is "Customers"
        And JBilling "LOGIN NAME" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And JBilling Click on "APPLY FILTERS" filter button
        And JBilling Click on row "1" in Table
        And JBilling Click on text link "Show all invoices"
        Then Invoice table is not empty
        And JBilling First cell value in first row is "parameter:ID & Type"
        When JBilling Click on row "1" in Table
        Then JBilling Value next to label "Invoice Number" is "parameter:ID & Type"
        And JBilling Value next to label "InvoiceType" is "ADVANCE"
        And JBilling Value next to label "Status" is "Unpaid"
        And Inner tables are not empty

        # Check orders in jbilling
        When JBilling top menu item is "Orders"
        And JBilling "LOGIN NAME" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And JBilling Click on "APPLY FILTERS" filter button
        Then Order table is not empty
        When JBilling Click on row "1" in Table
        Then JBilling Value next to label "User Name:" is "parameter:Id Billing customer & persoon/familie sleutel"
        And JBilling Value next to label "OrderType" is "RECURRING_ADVANCE_PAYMENT"
        And JBilling Value next to label "OrderLineType" is "ADVANCE" in Inner Table
        And Inner tables are not empty
