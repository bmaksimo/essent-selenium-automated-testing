@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter Elements

    Background:
        Given I logged in to DWP as Billing

    Scenario: Checking if filter elements, defined for "Billing" Left Tab, are available
        When Left menu is billing
        Then Available filters are:
            |Task number|
            |Task ID|

    Scenario: Checking whether filter elements, defined for each Left Tab -> Top Tab combination, are available
        When Left menu is billing
        And  Top menu item is My Accounts
        Then Available filters are:
            |EAN|
            |Account number|
        When Top menu item is Contracts
        Then Available filters are:
            |Contract number|
            |Delivery address street|
        When Top menu item is Cases
        Then Available filters are:
            |Case number|
            |Status|
            |Priority|
