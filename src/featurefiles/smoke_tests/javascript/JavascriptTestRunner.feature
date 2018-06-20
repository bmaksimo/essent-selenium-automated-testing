@SMOKE
Feature: Javascript using in DWP testing

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: We can access the main filter elements
        When Left Menu Item is Sales Marketing

        Then  Menu mainMenu has link id sales-marketing-link
        And   Menu subMenu has link id market-transactions-dashboard-link
        And   I smoke test all Javascript functions
