@REGRESSION
@DWP
@FILTER
@BASIC
Feature: DWP Filter Options

    Background:
        Given I logged in to DWP as BusinessDeskB2C

    Scenario: Checking if filter elements, defined for Sales Marketing Left Tab, are available
        When Left menu is sales-marketing
        Then Available filters are:
            |Number|
            |Name|

    Scenario: Checking whether Filter elements, defined for each Left Tab -> Top Tab combination, are available
        Given Left menu is sales-marketing
        When Top menu item is Market Transactions
        Then Available filters are:
            |EAN|
            |Process ID|
            |Parent process ID|
            |Cancellation reason|
            |Hijack info|
            |Rejection reason|
            |Company Name|
            |Company Number|
            |Account number|
            |Module|
            |Label|
            |EC Status|
            |Account segment|
            |BRP|
            |DGO|
            |Supplier|
            |Effective date after|
            |Effective date before|
            |Created after|
            |Created before|
        When Top menu item is Leads
        Then Available filters are:
            |Company name|
            |Only "do not call"|
        When Top menu item is My Accounts
        Then Available filters are:
            |EAN|
        When Top menu item is Accounts
        Then Available filters are:
            |EAN|
        When Top menu item is Quotes
        Then Available filters are:
            |Quote number|
        When Top menu item is Contracts
        Then Available filters are:
            |Contract number|
        When Top menu item is Euroccor Quotes
        Then Available filters are:
            |Quote number|
        When Top menu item is Cases
        Then Available filters are:
            |Case number|
