@REGRESSION
@DWP
@FILTER
@BASIC
Feature: DWP Filter Options

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: Checking if filter elements, defined for Sales Marketing Left Tab, are available
        When Left Menu Item is Sales Marketing
        Then Available filter elements are:
           |Number|
           |Name|

    Scenario: Checking whether Filter elements, defined for each Left Tab -> Top Tab combination, are available
        Given Left Menu Item is Sales Marketing
        When Top Menu Item is Market Transactions
        Then Available filter elements are:
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
        When Top Menu Item is Leads
        Then Available filter elements are:
            |Company name|
            |Only "do not call"|
        When Top Menu Item is My Accounts
        Then  Available filter elements are:
            |EAN|
        When Top Menu Item is Accounts
        Then Available filter elements are:
            |EAN|
        When Top Menu Item is Quotes
        Then Available filter elements are:
            |Quote number|
        When Top Menu Item is Contracts
        Then Available filter elements are:
            |Contract number|
        When Top Menu Item is Euroccor Quotes
        Then Available filter elements are:
            |Quote number|
        When Top Menu Item is Cases
        Then Available filter elements are:
            |Case number|


