@ODOO
@B2C
@CREDIT-AND-CONTROL
Feature: NUAT-5019 Step 3. Generate mock CODA file for account,  import the generated coda file /and check payment for advance invoice/

    Background:
        Given I logged in to Odoo as t.geets

    @CREATE-CODA
    Scenario: Create CODA file in Odoo
        Given Cleanup Odoo CODA files
        When Odoo top menu is Accounting
        And  Odoo left menu is Customers
        And Odoo filter is parameter:accountNumber
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the "1st" row is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Modal button "Close" is clicked

    @IMPORT-CODA
    @NUAT-5019-STEP-3
    Scenario: Import CODA file in Odoo
        When Odoo top menu is Accounting
        When Odoo left menu is CODA Processing->Import CODA Files
        Then Odoo file upload dialog is Import CODA File
        Then CODA file is parameter:codaFile
        And  Odoo file upload confirm button is Import
        And  Odoo file import report
        And Modal button "View Bank Statement" is clicked
        When Column "Reference" of the "1st" row is clicked
        And Modal button "Close" is clicked



