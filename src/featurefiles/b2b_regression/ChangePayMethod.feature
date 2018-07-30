@B2B_REGRESSION
Feature: DWP testing: NUAT-373

	  Background:

        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And Filter element "Name" input is "%Steven%"
        And Filter element "Account number" input is "150638828"
        When Click on link in View List at 1st row and "Account Number & Name" column
        And Overview is Workflows
        When Click on Start new market scenario
#        And Open Select Contractline
#        And Input EAN in search field
