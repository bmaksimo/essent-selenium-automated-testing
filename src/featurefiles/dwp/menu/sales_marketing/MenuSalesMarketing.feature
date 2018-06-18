@DWP
@BASIC
@MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: Upper menu items are available for Left Menu Item
        Given Available Left Menu items are:
            | Sales Marketing       |
            | Contracting Switching |
            | Billing               |
            | Credit Management     |
            | Finance               |
            | Service               |
            | Ess                   |
            | Admin                 |
        And Left Menu Item is Sales Marketing
        Then View title is 'Quotation - Group tasks'
        And Available Top Menu Items are:
            | Market Transactions |
            | Leads               |
            | My Accounts         |
            | Accounts            |
            | Quotes              |
            | Contracts           |
            | Pricing tool        |
            | Campaigns           |
            | Euroccor Quotes     |
        And The following Plus menu items are available at positions:
            |position|item|
            |4       |BILLING    |
