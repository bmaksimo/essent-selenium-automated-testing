@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow:
    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> UP/TC2 -> Create new quote (B2B)"
        And Top Arrow button is Back
        And Left Menu Item is Sales Marketing
        And Top Menu Item is Market Transactions
        And Top Action is Filters
        And Filter element "EAN" input is "541444625522734400"
        And Filter element "Module" selection is "ESSENT IS HIJACKED"
        Then View List is empty
