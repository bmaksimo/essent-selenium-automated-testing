@DWP
@SMOKE
Feature: view list functions, scrapping and storing info from page
    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be
    Scenario: Navigate, then store values selected in list view
        When Left menu is contracting-switching
        And Top menu item is Marktberichten
        Then View list header is "Marktberichten" appears within 25 seconds

        When Top action is Filters
        And "Module" selection is "CANCEL"
        And "Label" selection is "By Essent"
        Then 3 List rows having cell value CANCEL By Essent at column Module & Label are selected
        And  Cell values from selected rows and column "EC Status & Effective date" are put to parameter "ec_status"
        And  Cell value from "1st" row and "Billing klant & Tariefdatum" column is put to parameter "id-billing-customer"
        And Selected List rows have cell value "CANCEL By Essent" at column "Module & Label"
