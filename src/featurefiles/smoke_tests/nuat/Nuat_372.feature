@SMOKE
@B2B_REGRESSION
Feature: DWP Test Nuat 372

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Accounts
        And Top Action is Filters
        And Filter element "B2C/B2B" selection is "B2B"
        And Filter element "Customer Type" selection is "Customer"
        And Filter element "Name" input is "%Steven%"
        And Filter element "Account number" input is "150638828"
        And Tap on element 150638828
        Then View List is empty
