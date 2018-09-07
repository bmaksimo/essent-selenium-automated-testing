@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


#    Scenario:
#        When b2b Account is selected by using filter on account page
#        And b2b New case for customer is logged through plus icon on the top right side
#        Then b2b Case details are visible when case is opened
#

    Scenario:
        When b2b Left menu is "Sales-marketing"
        When b2b Top menu is "Accounts"
        When b2b Account is selected by using "accountId" in filter
        And b2b Plus menu is "Service"
        And b2b "Log a case for account" is selected in Service
        And b2b New case for account is created
        Then b2b Case details are visible when case is opened
