@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in to DWP as CreditManagement

    Scenario: Filter elements defined for Credit Left Menu are available
        When Left Menu Item is credit-management
        And  Top Menu Item is Accounts
        Then Available filter elements are:
            |EAN|
            |Account number|
