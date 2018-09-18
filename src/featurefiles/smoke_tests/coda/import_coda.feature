@ODOO
@CODA
Feature: Import a coda file

    Background:
        Given I logged in to Odoo as payments.testautomation

    Scenario: Create a CODA file
        When Odoo top menu is Accounting
        And Odoo left menu is Import CODA Files
