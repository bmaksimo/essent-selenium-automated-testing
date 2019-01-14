@ODOO
@B2C
@CREDIT-AND-CONTROL
Feature: NUAT-5019 Step 3. Import a coda file and make payment for advanced invoice

    Background:
        Given I logged in to Odoo as "t.geets"

    @IMPORT-CODA
    @NUAT-5019-STEP-3
    Scenario: Create and match CODA file in Odoo

        When Odoo top menu is "Accounting"
        And  Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        When CODA file is selected
        And  Odoo file upload confirm button is "Import"
        And  Odoo file import report



