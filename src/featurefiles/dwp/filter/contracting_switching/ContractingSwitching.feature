@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in to DWP as BusinessDeskB2B

    Scenario: Checking if filter elements, defined for Contracting Switching Left Tab, are available
        When Left menu is contracting-switching
        Then Available filters are:
            |Number|
            |Name|
            |Account name|
            |Account number|
    Scenario: Checking whether filter elements, defined for each Left Tab -> Top Tab combination, are available
        When Left menu is contracting-switching
        And Top menu item is MS: Send ILC
        Then Available filters are:
            |Account number|
            |Name|
            |EAN|
