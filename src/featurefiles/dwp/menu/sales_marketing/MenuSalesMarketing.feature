@DWP
@BASIC
@MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in to DWP as BusinessDeskB2B

    Scenario: Upper menu items are available for Left Menu Item
        Given Available left menu items are:
            | sales-marketing       |
            | contracting-switching |
            | service               |
            | tasks                 |
        And Left menu is sales-marketing
        Then View list header is "Quotation - Group tasks"
        And Available top menu items are:
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
