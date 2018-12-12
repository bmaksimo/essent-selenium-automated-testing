@ODOO
@DEV
@CHECK-CLOSED-PAYMENT
Feature: Import a coda file

    Background:
        Given I logged in to Odoo as p.paulussen

    Scenario: Check if the last payment is closed
        When Odoo top menu is Accounting
        And  Odoo left menu is Bank Statements
        Then The value in the column "Status" of the "1st" row is "Closed"



