@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be
    Scenario:
        When Left Menu Item is sales-marketing
        And Top Menu Item is Market Transactions
        And Top Action is Filters
        #And Filter element "EAN" input is "541444625522734400"
        And "Module" selection is "CANCEL"
        #Then 1st List element has cell value Closed at column EC Status & Effective Date
        Then Select 3 List rows having cell value CANCEL By market at column Module & Label
        And  Store cell values of selected list rows at column "EC Status & Effective Date" as "ec_status"
        And Selected List rows have cell value "CANCEL By market" at column "Module & Label"


