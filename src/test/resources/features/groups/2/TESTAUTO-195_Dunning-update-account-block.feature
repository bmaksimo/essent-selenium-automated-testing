@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-195 Dunning-update-account-block

     Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @TESTAUTO-195
    Scenario: update-account-block
        When Left menu is "contracting-switching"
#        And Sleep for 60 seconds
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
        And Account block Start date is today
        And Account block End date is today plus 7 days

        When Plus action of "1" element from "accountBlockReasonsForAccountList" and click on "Update"
        And "Einddatum" date is "now"
        And Date is saved
        And Changes are confirmed
        Then Table "Lijst blokkeringen" contains value "parameter:endDate" at column "Einddatum" retrying 60 times
        Then Account block start and end dates are the same
        
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And  Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Button Account Blocks is clicked
        And Account Block Reason is E-plus
        And Account Block is Active
        And Account Block Start date is today
        And Account Block End date is "0" days from today




