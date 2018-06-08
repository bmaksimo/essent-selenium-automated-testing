@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter Elements

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: Checking if filter elements, defined for "Billing" Left Tab, are available
        When Left Menu Item is Billing
        Then Available filter elements are:
            |Task number|
            |Task ID|

    Scenario: Checking whether filter elements, defined for each Left Tab -> Top Tab combination, are available
        When Left Menu Item is Billing
        And  Top Menu Item is My Accounts
        Then Available filter elements are:
            |EAN|
            |Account number|
        When Top Menu Item is Contracts
        Then Available filter elements are:
            |Contract number|
            |Delivery address street|
        When Top Menu Item is Cases
        Then Available filter elements are:
            |Case number|
            |Status|
            |Priority|
