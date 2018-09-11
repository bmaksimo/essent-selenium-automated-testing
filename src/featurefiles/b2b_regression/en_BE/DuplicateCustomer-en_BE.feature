@B2B_REGRESSION
Feature: Dwp test for duplicating customer

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Accounts
        And Top action is Filters
        And "Name" input is "%Steven%"
        And "Account number" input is "150638828"
        And Click on link in View List at 1st row and "Account Number & Name" column
        And Plus menu is "Service -> Duplicate account"
        When "Company name" input is "Van Hauwaert Steven - Test Nuat 372"
        Then Changes are confirmed
        And Top Arrow button is Up
        And Search input is Van Hauwaert Steven - Test Nuat 372
        Then Customer "Van Hauwaert Steven - Test Nuat 372" is found

