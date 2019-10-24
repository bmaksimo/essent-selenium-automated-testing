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
        When "Create_Quote" flow is started
        And Data is prepared for Create quote request for "prospect" and meter open is "On" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"
        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"
        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

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
        And "Verkoop manueel" is open
        And Manage invoice block is clicked
        And Create new invoice block button is clicked
        And Invoice block reason is "WCO"
        And End date is "1 month from now"
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
