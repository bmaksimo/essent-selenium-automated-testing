@DWP
@_SMOKE
Feature: Javascript DWP testing

    Background:
        Given I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario: We can access the main gui elements
        When Left menu is sales-marketing
        And  Top menu item is Market Transactions
        And  View list header is "Market Transactions"

        When Click on link in View List at 1st row and "Account & EAN" column
        And  Top arrow button is Up
        When Click on link in View List at 1st row and "Account & EAN" column
        And  Dashboard menu is Contracts
        And  Top arrow button is Up
        And  Plus menu is "Switching -> Market Transaction Tasks"
        Then View list header is "Tasks market transactions"
        And  Find web element by Xpath "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='Market Transactions']"

        When Top action is Filters
        And  Top action is Plus Menu
        And  Top action is Filters
        And  Top action is Plus Menu
