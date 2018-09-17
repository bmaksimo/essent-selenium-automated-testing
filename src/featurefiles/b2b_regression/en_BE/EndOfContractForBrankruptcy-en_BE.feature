@_B2B_REGRESSION
Feature: End of contract for bankruptcy

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Accounts
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Account number" input is "151004631"
        When Click on link in View List at 1st row and "Account Number & Name" column
        Then Dashboard menu is Workflows
        When Click on Start new market scenario
        And Click Select Contractline
        And EAN check box
        Then Changes are confirmed
        And "Module" input is "INITIATE STOP ACCESS"
        And "Label" input is "Non-Residential End-of-Contract"
        And "Effective Date" date is "now"
        Then Changes are confirmed
