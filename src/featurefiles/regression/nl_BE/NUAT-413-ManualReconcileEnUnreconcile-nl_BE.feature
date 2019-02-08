@ODOO
@B2B
@REGRESSION
@PAYMENTS
    Feature: NUAT-413: Manual reconcile en unreconcile

    Background:
        Given  I logged in to Odoo as "t.geets"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    Scenario: Manual reconcile and unreconcile
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Journal Entries"
        And Create new Journal Entries is clicked
        And Journal is "Diverse dagboek klanten"
        And Date document is now
        And New item is
            | name | partner | account | debit | credit |
            | new1 | parameter:accountNumber | 580100 | 0 | 10 |
            | new2 | parameter:accountNumber | 580100 | 10 | 0 |
        Then Save and Post journal entry
        When  Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        And Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Button "Journal Items" is clicked
        When Mark first two journal items one with credit and one with debit "10"
        And More menu is "Reconcile Entries"
        And Confirm action
        Then Reconcile number is shown
        When Mark first two journal items one with credit and one with debit "10"
        And More menu is "Unreconcile Entries"
        And Confirm action
        Then Reconcile number is removed


