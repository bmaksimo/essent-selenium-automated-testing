@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING
@Unstable

Feature: TESTAUTO-204 Dunning-create-invoice-block

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-204
    Scenario: Create-invooice-block
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now"
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
        And Contracted EAN exists on account

        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Billing run "RECURRING" is triggered with process date "1 month from now"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 10 times
        And Plus action of "1" element from "TransactionsOnAccount" and click on "Plaats aanmaningsblokkade op factuur"
        And "Reden factuurblok" selection is "Payment mismatch"
        And "Startdatum" date is "now"
        And "Einddatum" date is "31 days from now"
        And Changes are confirmed waiting for 5 seconds
        Then Table "Transacties" contains check mark at column "Geblokkeerd?"

        When Click on link in View List at "1st" row and "ID & Type" column polling 60 seconds
        And Table "Lijst met factuur blokkeringen" contains value "Payment mismatch" at column "Reden" retrying 30 times
        And Table "Lijst met factuur blokkeringen" contains value "true" at column "Actief" retrying 30 times
        And Invoice Block End date is "31" days from today

        Given I renew login to Odoo as "role_essent_ccm_user"
        And Odoo top menu is "Accounting"
        And Odoo left menu is Customers
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Right box button "Invoice Blocks" is clicked
        And Reason is "Payment mismatch" on Invoice Blocks page
        And Start date is today on Invoice Blocks page
        And End date is "31" days from today on Invoice Blocks page
