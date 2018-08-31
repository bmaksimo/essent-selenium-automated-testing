#@B2B_REGRESSION
Feature: End of contract for bankruptcy

	  Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Account number" input is "151004631"
        When Click on link in View List at 1st row and "Account Number & Name" column
        Then Dashboard menu is Workflows
        When Click on Start new market scenario
        And Click Select Contractline
        And EAN check box
        Then Select button
        And "Module" selection is "INITIATE STOP ACCESS"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" selection is "$today"
        Then Select button
