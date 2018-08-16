@SMOKE
@B2B_REGRESSION
Feature: DWP Test Nuat 372

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be


    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And "Name" input is "%Steven%"
        And "Account number" input is "150638828"
        And Click on link in View List at 1st row and "Account Number & Name" column
        And Plus Menu is "Service -> Duplicate account"
        When "Company name" input is "Van Hauwaert Steven - Test Nuat 372"
        When Confirm Change
        And Top Arrow button is Up
        And Search input is Van Hauwaert Steven - Test Nuat 372
        Then Validate customer duplicate

