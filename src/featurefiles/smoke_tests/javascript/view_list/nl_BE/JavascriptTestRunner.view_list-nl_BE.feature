@DWP
@_SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be
    Scenario:
        When Left menu is contracting-switching
        And Top menu item is Marktberichten
        Then View list header is "Marktberichten" appears within 25 seconds

        When Top action is Filters
        And "Module" selection is "CANCEL"
        And "Label" selection is "By Essent"
        Then 3 List rows having cell value CANCEL By Essent at column Module & Label are selected
        And  Selected list rows at column "EC Status & Effective date" are put to global parameter "ec_status"
        And Selected List rows have cell value "CANCEL By Essent" at column "Module & Label"
