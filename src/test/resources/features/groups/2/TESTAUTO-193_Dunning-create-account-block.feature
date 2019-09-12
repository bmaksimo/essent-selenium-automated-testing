@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-193 Dunning-create-account-block

     Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @TESTAUTO-193
    Scenario: create-account-block
        When Left menu is "contracting-switching"
        And Sleep for 60 seconds
        And Top menu item is "Klanten"
        When B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "contracting-switching" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
        And Dashboard menu is "Details"
        And Click on "CREEREN BLOKKERING"
        And "Block reason" selection is "E-plus"
        And "Einddatum" date is "7 days from now"
        And Sleep for 10 seconds
        And Changes are confirmed
        And Sleep for 60 seconds

        Then Table "Lijst blokkeringen" contains value "E-plus" at column "Reden" retrying 60 times
        And Table "Lijst blokkeringen" contains value "true" at column "Actief" retrying 60 times
        And Account Block Start date is today
        And Account Block End date is 7 days from today

        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Button Account Blocks is clicked
        And Reason is "E-plus" on Account Blocks page
        And Active is checked on Account Blocks page
        And Start date is today on Account Blocks page
        And End date is 7 days from today on Account Blocks page
