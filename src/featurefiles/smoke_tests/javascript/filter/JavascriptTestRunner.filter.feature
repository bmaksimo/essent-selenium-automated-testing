@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow:

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Market Transactions
        And Top Action is Filters
        And Filter element "EAN" input is "541444625522734400"
        And Filter element "Created after" date input is "$today + 3months"
        And Filter element "Created before" date input is "$today +  4months"
        And Filter element "Module" selection is "ESSENT IS HIJACKED"
        Then View List is empty

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP/TC2 - to renew contracts"
        Then View List Header is "UP-TC2 - to renew contracts"
        When Top Action is Filters
        And  Filter element "Account number" input is "6574"
        Then View List is empty



