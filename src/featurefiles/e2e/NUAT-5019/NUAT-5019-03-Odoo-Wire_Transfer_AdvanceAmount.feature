@ODOO
@B2C
@CREDIT-AND-CONTROL
Feature: NUAT-5019 Step 3. Import a coda file and make payment for advanced invoice

    Background:
        Given I logged in to Odoo as "role_essent_ccm_user"

    @IMPORT-CODA
    @NUAT-5019-STEP-3
    Scenario: Create and match CODA file in Odoo

        # 3 - Download CODA
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
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report contains success string "Number of statements processed : 1"

        #And Modal button "View Bank Statement" is clicked
        #And Wait for 30 seconds
        #When Column "Reference" of the "1st" row is clicked
        #And Bank Statement "Close" button is clicked



