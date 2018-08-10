@B2B_REGRESSION
Feature: Change Pay Method

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario:
        Given Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And Filter element "B2C/B2B" selection is "B2B"
        And Filter element "Account number" input is "150638828"
        And Click on link in View List at 1st row and "Account Number & Name" column
        And Overview is Details
#        And Detail "Billing customer" plus action Billing customer id is "<string>"
