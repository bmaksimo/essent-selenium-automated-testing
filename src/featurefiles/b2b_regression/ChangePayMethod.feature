@B2B_REGRESSION
@PAY
Feature: Change Payment Method

    Background:
        Given I logged in to DWP as b.maksimovic@levi9.com

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Accounts
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Customer Type" input is "CUSTOMER"
        And Click on link in View List at 1st row and "Account Number & Name" column
        And Dashboard menu is Details
        Then View list header is "Billing customer"
        When Click on link in "Billing customer" View List at 1st row and "Plus Action" column
        And Row actions "Update" is clicked
        Then Modal "Update billing customer" is displayed
        When Payment method is switched and IBAN is NL43ABNA0978459932
        And Modal Save is clicked
        Then 1st List element has updated cell value at column "Payment terms & payment method"
