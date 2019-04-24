@ODOO
@B2B
@REGRESSION
@PAYMENTS
@UNSTABLE
Feature: NUAT-413: Manual reconcile en unreconcile

    Background:
        Given  I logged in to Odoo as "role_essent_ccm_user"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    @NUAT-413
    Scenario: Manual reconcile and unreconcile
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Journal Entries"
        And Create new Journal Entries is clicked
        And Journal is "Diverse dagboek klanten"
        And Date document is now
        And New item is
            | name | partner                 | debit | credit |
            | new1 | parameter:accountNumber | 0     | 10     |
            | new1 | parameter:accountNumber | 10    | 0      |
        Then Save journal entry
        When Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        And Column "Account Number" with value "parameter:accountNumber" is clicked
        Then Button "Journal Items" is clicked
        When Mark first two journal items one with credit and one with debit "100"
        And More menu is "Reconcile Entries"
        And Confirm action
        Then Reconcile number is shown
        When Mark first two journal items one with credit and one with debit "100"
        And More menu is "Unreconcile Entries"
        And Confirm action
        Then Reconcile number is removed


