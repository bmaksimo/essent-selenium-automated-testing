@ODOO
@B2B
@REGRESSION
@PAYMENTS

Feature: NUAT-423: Manual reconcile en unreconcile

    Background:
        Given  I logged in to Odoo as t.geets
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    Scenario: Manual reconcile
        When
        When Odoo top menu is Accounting
        And Odoo left menu is Journal Entries
        And Create new Journal Entries is clicked
        And Journal is "Diverse dagboek klanten"
        And Date document is now
        And New item is
            | name | partner | account | debit | credit |
            | new1 | parameter:contractNumber | 580100 | 0 | 10 |
            | new2 | parameter:contractNumber | 580100 | 10 | 0 |
        And Save and Post journal entry
        And  Odoo left menu is Customers
        And Odoo filter is parameter:contractNumber
        When Column "Account Number" with value "parameter:contractNumber" is clicked
        And Button "Journal Items" is clicked

