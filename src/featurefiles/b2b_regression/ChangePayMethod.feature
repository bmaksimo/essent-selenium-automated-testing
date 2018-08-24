@B2B_REGRESSION
Feature: Change Pay Method

    Background:
        Given I logged in to DWP as b.maksimovic@levi9.com

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Account number" input is "151004569"
        When Click on link in View List at 1st row and "Account Number & Name" column
        And Dashboard menu is Details
        And View List Header is "Billing customer"
#        When Click on link in View List at 1st row and "" column
#        And "Billing customer" list item at 1st plus action
        And Selenium click on plus
