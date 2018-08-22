@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in to DWP as CreditManagement

    Scenario: Filter elements defined for Credit Left Menu are available
        When Left menu is credit-management
        And  Top menu item is Accounts
        Then Available filters are:
            |EAN|
            |Account number|
