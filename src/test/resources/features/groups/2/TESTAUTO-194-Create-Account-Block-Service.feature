@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-194 Dunning-create-account-block-service

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @TESTAUTO-194
    Scenario: create-account-block
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        When B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        When Plus menu is "Service -> Dunning stop on customer-NEW"
        And "Block reason" selection is "Official complaint"
        And "Startdatum" date is "now"
        And Changes are confirmed
        When Dashboard menu is "Details"

        And Table "Lijst blokkeringen" contains value "Official complaint" at column "Reden" retrying 30 times
        And Table "Lijst blokkeringen" contains value "true" at column "Actief" retrying 30 times
        And Account Block Start date is today

        Given I renew login to Odoo as "role_essent_ccm_user"
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Button Account Blocks is clicked
        And Reason is "Official complaint" on Account Blocks page
        And Active is checked on Account Blocks page
        And Start date is today on Account Blocks page
        And End date is 7 days from today on Account Blocks page

