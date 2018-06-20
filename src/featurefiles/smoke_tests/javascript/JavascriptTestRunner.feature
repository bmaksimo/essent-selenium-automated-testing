@SMOKE
Feature: Javascript DWP testing

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: We can access the main gui elements
        When Left Menu Item is Sales Marketing

        Then  Menu mainMenu has link id sales-marketing-link
        And   Menu subMenu has link id market-transactions-dashboard-link
        And   Plus Menu is "Sales -> UP/TC2 -> Pricing tool"
        And   I smoke test all Javascript functions
