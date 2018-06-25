@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow:
    Scenario:
        When Left Menu Item is Sales Marketing
        And Top Menu Item is Market Transactions
        And Top Action is Filters
        And Filter element "EAN" input is "541444625522734400"
        And Filter element "Module" selection is "MOVE IN"
        #Then 1st List element has cell value Closed at column EC Status & Effective Date
        Then Select 1st List row having cell value Closed at column EC Status & Effective Date
