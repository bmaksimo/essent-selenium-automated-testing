@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be
    Scenario:
        When Left Menu Item is contracting-switching
        And Top Menu Item is Marktberichten
        Then View List Header is "Marktberichten" appears within 25 seconds

        When Top Action is Filters
        And "Module" selection is "CANCEL"
        Then 3 List rows having cell value CANCEL By Essent (Secured) at column Module & Label are selected
        And  Selected list rows at column "EC Status & Effective date" are put to global parameter "ec_status"
        And Selected List rows have cell value "CANCEL By Essent (Secured)" at column "Module & Label"


