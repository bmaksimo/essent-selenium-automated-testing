@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Market Transactions
        And Top Action is Filters
        And "EAN" input is "541444625522734400"
        And "Created after" date input is "$today + 3months"
        And "Created before" date input is "$today +  4months"
        And "Module" selection is "ESSENT IS HIJACKED"
        Then View List is empty

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP/TC2 - to renew contracts"
        Then View List Header is "UP-TC2 - to renew contracts"
        When Top Action is Filters
        And "Account number" input is "6574"
        Then View List is empty



