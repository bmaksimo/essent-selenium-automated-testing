@DWP
@BASIC
@MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in as admin on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: Upper menu items are available for Left Menu Item
        When Left Menu Item 'SALES_MARKETING' is made active
        Then Content page contains title 'Quotation - Group tasks'
        And The following Top Menu Items are available:
          #  | SALES_MARKETING_QUOTATION_TASKS|
            | MARKETTRANSACTIONS_DASHBOARD|
            |LEAD_LIST|
            |MY_ACCOUNTS_LIST|
            |ACCOUNTS_LIST|
            |QUOTES_LIST|
            |CONTRACT_LIST|
            |PRICING_TOOL_DASHBOARD|
            |CAMPAIGN_LIST|
            |EUROCCOR_QUOTES|
            |CASES|
        And These Plus menu items are available at the following positions:
            |position|item|
            |4       |BILLING    |
