@DWP
@CORE
@CORE-VIEW_LIST_MODEL
@OUTPUT_PARAMETERS
Feature: View list model functions, extracting data from web page and passing parameters to the other scenarios
    Background:
        Given   I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    Scenario: Navigate, then store values selected in list view
        When Left menu is "contracting-switching"
        And Top menu item is "Marktberichten"
        Then View list header is "Marktberichten" appears within 25 seconds

        When Top action is "Filters"
        And "Module" selection is "CANCEL"
        And "Label" selection is "By Essent"
        Then "3" List rows having cell value "CANCEL By Essent" at column "Module & Label" are selected
        And Selected List rows have cell value "CANCEL By Essent" at column "Module & Label"
        And Cell values from selected rows and column "EC Status & Effective date" are checked

        When Top menu item is "Klanten"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked


