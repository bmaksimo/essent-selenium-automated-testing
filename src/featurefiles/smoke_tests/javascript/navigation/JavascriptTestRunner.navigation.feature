@SMOKE
Feature: Javascript DWP testing

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: We can access the main gui elements
        When Left Menu Item is Sales Marketing
        Then Menu mainMenu has link id sales-marketing-link
        And  Menu subMenu has link id market-transactions-dashboard-link
        And  Top Menu Item is Market Transactions
        And  View List Header is "Market Transactions"

        When Click on link in View List at 1st row and "Account & EAN" column
        And  Top Arrow button is Up
        When Click on link in View List at 1st row and "Account & EAN" column
        And  Overview is Contracts
        And  Top Arrow button is Up
        And  Plus Menu is "Switching -> Market Transaction Tasks"
        Then View List Header is "Tasks market transactions"
        And  Find web element by Xpath "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='Market Transactions']"
        And  I smoke test all Javascript functions
        
        When Top Action is Filters
        And  Top Action is Plus Menu
        And  Top Action is Filters
        And  Top Action is Plus Menu
