@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@API
@NEWFEATURE

Feature: TESTAUTO-394 Release a Guarantee

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-394
    Scenario: Release a Guarantee
        When "Create_Quote" flow is started
        And Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on "parameter:accountNumber" link

         # Set up guarantee amount
        When Dashboard menu is "Sales" waiting for 5 seconds
        And Plus action of "1" element from "QuotesOnAccount" and click on "Wijzig KA status"
        And "Klantacceptatie" selection is "Waarborg"
        And "Waarborgbedrag" input is "200"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Verstuurd naar de klant - Waarborg" at column "Type & status"

        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum ondertekening" date is "now"
        And Changes are confirmed
        Then Table "Offertes" has matching value "Sales" at column "Type & status"
        And Table "Offertes" has matching value "Handtekening ontvangen - Waarborg" at column "Type & status"

        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        Then Changes are confirmed

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (GUARANTEE)" at column "ID & Type" retrying 10 times
        And Copy invoice amount from the first invoice

        # CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is Customers
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the first row with "Amount receivable" is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Sleep for 7 seconds
        And Close the pop-up

        When Odoo left menu is "CODA Processing->Import CODA Files"
        And Odoo file upload dialog is "Import CODA File"
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report contains success string "Number of statements processed : 1"

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 800 seconds

        When Dashboard menu is "Billing"
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Waarborg terugbetalen"
        And Sleep for 2 seconds
        And "Releasedate" date is "now"
        Then Changes are confirmed

        When Table "Transacties" contains value "CNW" at column "ID & Type" retrying 10 times
        Then CNW and VKW invoice have same invoice amount





