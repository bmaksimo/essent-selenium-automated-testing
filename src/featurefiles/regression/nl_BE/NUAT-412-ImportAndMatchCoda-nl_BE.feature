@ALL
@DWP
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-412 part: Create / import coda file
    Background:
        # 1 - Create active B2B UP contract
        Given B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
    @NUAT-412
    Scenario: Create active contract TK1
        Given I logged in to DWP as "billing.testautomation@essent.be"

        # 2 - invoice run advance
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"

        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at "1st" list row
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is "Up"
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds

        # 3 - Download CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the first row with "Amount receivable" is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded

        # 4 - import and match CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"

        When CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        Then Odoo file import report contains success string "Number of statements processed : 1"

        When Modal button "View Bank Statement" is clicked
        And Column "Reference" of the "1st" row is clicked
        Then Modal button "Close" is clicked
