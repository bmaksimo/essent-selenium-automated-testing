@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario:
        When Left Menu Item is contracting-switching
        And Top Menu Item is Marktberichten
        And Top Action is Filters
        And "Aangemaakt na" date is "$today - 3months"
        And "Aangemaakt voor" date is "$today +  1 day"
        And "EAN" input is "541444625522734400"
        And "Module" selection is "ESSENT IS HIJACKED"
        Then View List is empty

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP/TC2 - to renew contracts"
        Then View List Header is "UP-TC2 - to renew contracts"
        When Top Action is Filters
        And "Account number" input is "6574"
        Then View List is empty



