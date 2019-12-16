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
        And Create active B2C contract with metering "Off" and sign date "35 days before now"

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
