@ALL
@DWP
@B2B
@REGRESSION
@JBILLING
Feature: NUAT-510 Triggering advance invoice run. Check is invoice created in DWP and jbilling

    Background:
        Given B2B Active Contract is
            | productType    | isFakeAddress | switchType       | meterType       | kwMax |
            | TC1            | FAKE          |  SUPPLIER SWITCH | YMR             | 50000 |
        And I logged in to DWP as "billing.testautomation@essent.be"

    @NUAT-510
    Scenario: NUAT-510: Trigger Invoice run process for B2B TK1
        And Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Plus menu is "Billing -> Start facturatierun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        And Modal dialog is "Start invoicerun"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:billingId"

        When Invoice run is scheduled
        And Sleep for 10 seconds
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is "Billing"

        Then "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds
        And "1st" List element with value at column "ID & Type" is checked

        Given I logged in to JBilling as "billing_testautomation"
        And JBilling top menu item is "Customers"
        And JBilling "LOGIN NAME" input is "parameter:billingId"
        And JBilling Click on "APPLY FILTERS" filter button
        And JBilling Click on row "1" in Table
        When JBilling Click on text link "Show all invoices"
        Then Invoice table is not empty
        And JBilling First cell value in first row is "parameter:ID & Type"
        When JBilling Click on row "1" in Table
        Then JBilling Value next to label "Invoice Number" is "parameter:ID & Type"
        And JBilling Value next to label "InvoiceType" is "ADVANCE"
        And JBilling Value next to label "Status" is "Unpaid"
        And Inner tables are not empty

        # Check orders in jbilling
        Given JBilling top menu item is "Orders"
        And JBilling "LOGIN NAME" input is "parameter:billingId"
        And JBilling Click on "APPLY FILTERS" filter button
        Then Order table is not empty
        When JBilling Click on row "1" in Table
        Then JBilling Value next to label "User Name:" is "parameter:billingId"
        And JBilling Value next to label "OrderType" is "RECURRING_ADVANCE_PAYMENT"
        And JBilling Value next to label "OrderLineType" is "ADVANCE" in Inner Table
        And Inner tables are not empty
