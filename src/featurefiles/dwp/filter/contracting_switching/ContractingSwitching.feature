@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: Checking if filter elements, defined for Contracting Switching Left Tab, are available
        When Left Menu Item is Contracting Switching
        Then Available filter elements are:
            |Number|
            |Name|
            |Account name|
            |Account number|
    Scenario: Checking whether filter elements, defined for each Left Tab -> Top Tab combination, are available
        When Left Menu Item is Contracting Switching
        And Top Menu Item is MS: Send ILC
        Then Available filter elements are:
            |Account number|
            |Name|
            |EAN|
