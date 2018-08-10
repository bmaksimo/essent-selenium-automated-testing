@DWP
@BASIC
@MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario: Upper menu items are available for Left Menu Item
        Given Available Left Menu items are:
            | sales-marketing       |
            | contracting-switching |
            | service               |
            | tasks                 |
        And Left Menu Item is sales-marketing
        Then View List Header is "Quotation - Group tasks"
        And Available Top Menu Items are:
            | Market Transactions |
            | Leads               |
            | Accounts            |
            | Quotes              |
            | Contracts           |
            | Cases               |
            | Pricing tool        |
        And The following Plus menu items are available at positions:
            |position|item|
            |3       |CONTRACTING    |
