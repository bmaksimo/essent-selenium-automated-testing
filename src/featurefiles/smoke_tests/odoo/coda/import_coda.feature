@ODOO
@CODA
Feature: Import a coda file

    Background:
        Given I logged in to Odoo as payments.testautomation

    Scenario: Create a CODA file
        When Odoo top menu is Accounting
        And  Odoo left menu is CODA Processing->Import CODA Files
        Then Odoo file upload dialog is Import CODA File
        When CODA file is selected
        And  Odoo file upload confirm button is Import
        And  Odoo file import report



