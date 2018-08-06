@B2B_REGRESSION
Feature: DWP testing: NUAT-373

	  Background:

        Given   I logged in in DWP as soapui_b2b
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And Filter element "B2C/B2B" selection is "B2B"
        And Filter element "Account number" input is "151105171"
        When Click on link in View List at 1st row and "Account Number & Name" column
        And Overview is Workflows
        When Click on Start new market scenario
        And Click Select Contractline
        And Search field input is "541443702465030408"
        And EAN check box
        And Submit button
#        When Break Point
