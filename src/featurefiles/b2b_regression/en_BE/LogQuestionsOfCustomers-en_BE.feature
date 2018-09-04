@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


    Scenario:
        When pgo Account is selected
#        When Left menu is sales-marketing
#        When Top menu item is Accounts
#        When Top action is Filters
#        And "Name" input is "%Steven%"
#        And "Account number" input is "150638828"
#        And Click on link in View List at 1st row and "Account Number & Name" column
#        And Plus menu is "Service -> Log a case for account"
        When pgo New case for customer is logged
        Then pgo Case details are visible when case is opened
        
