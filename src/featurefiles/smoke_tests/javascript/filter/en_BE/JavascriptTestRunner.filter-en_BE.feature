@_SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Market Transactions
        And Top action is Filters
        And Filter element "EAN" input is "541444625522734400"
        And Filter element "Created after" date input is "$today + 3months"
        And Filter element "Created before" date input is "$today +  4months"
        And Filter element "Module" selection is "ESSENT IS HIJACKED"

        When Left menu is sales-marketing
        And Top menu item is Market Transactions
        And Top action is Filters
        And "Created after" date is "$today + 3months"
        And "Created before" date is "$today +  4months"
        And "EAN" input is "541444625522734400"
        And "Module" selection is "ESSENT IS HIJACKED"
        Then View List is empty

    Scenario:
        When Top action is Plus Menu
        And Plus menu is "Contracting -> UP/TC2 - to renew contracts"
        Then View list header is "UP-TC2 - to renew contracts"
        When Top action is Filters
        And "Account number" input is "6574"
        Then View List is empty
