@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu  Item is Accounts
        And Top Action is Filters
        And "Name" input is "%Steven%"
        And "Account number" input is "150638828"
        And Click on link in View List at 1st row and "Account Number & Name" column
        And Plus Menu is "Service -> Log a case for account"
