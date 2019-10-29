@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-205 Creation of dunning bundle

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-205
    Scenario: Creation of dunning bundle
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
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        When Billing run "RECURRING" is triggered with process date "1 month from now"

        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" within 120 seconds
        And Table "Transacties" contains value "Issued" at column "Extra info" within 1800 seconds
        Then Click on link in View List at "1st" row and "ID & Type" column polling 60 seconds

        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And  Odoo left menu is "Dunning Bundles"
        And Advanced search is
            |      field     |   operator  |          value          |
            | Account Number | is equal to | parameter:accountNumber |
        Then Dunning bundle is present
