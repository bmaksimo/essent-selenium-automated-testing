@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-285 Dunning-invoice-block-from-Odoo

    Background:
        Given I login as API user "soapui_b2c"
    @TESTAUTO-285
    Scenario: Invoice block from Odoo
        #1 - API contract creation
        And Create active B2C contract with metering "On" and sign date "35 days before now"

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #Create invoice via jBilling client call
        When Billing run "RECURRING" is triggered with process date "now"
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 10 times

        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And  Odoo left menu is Customers
        And Odoo filter is "parameter:accountNumber"
        Then Column "Account Number" with value "parameter:accountNumber" is clicked

        When Button "Outstanding" is clicked
        And "VERKOOP AMR MMR MANUEEL (EUR) (1)" is open
        And Manage invoice block is clicked
        And Create new invoice block button is clicked
        And Invoice block reason is "WCO"
        And End date is "31 days from now"
        Then Save invoice block button is clicked

        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains check mark at column "Geblokkeerd?"

        When Click on link in View List at "1st" row and "ID & Type" column polling 60 seconds
        Then Table "Lijst met factuur blokkeringen" contains value "WCO" at column "Reden" retrying 30 times
        And Invoice Block End date is "31" days from today
