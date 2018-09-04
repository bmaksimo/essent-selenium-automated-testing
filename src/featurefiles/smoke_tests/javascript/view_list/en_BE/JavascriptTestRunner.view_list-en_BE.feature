@DWP
@_SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be
    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Market Transactions
        And Top action is Filters
        #And Filter element "EAN" input is "541444625522734400"
        And "Module" selection is "CANCEL"
        #Then 3 List rows having cell value CANCEL By market at column Module & Label are selected
        Then 3 List rows having cell value CANCEL By Essent (Secured) at column Module & Label are selected
        And  Selected list rows at column "EC Status & Effective Date" are put to global parameter "ec_status"
        And Selected List rows have cell value "CANCEL By Essent (Secured)" at column "Module & Label"
