@B2B_REGRESSION
@SMOKE
Feature: Javascript DWP testing

    Background:
        Given I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario: We can access the main gui elements
        When Left menu is contracting-switching
        And Top menu item is Marktberichten
        Then View list header is "Marktberichten" appears within 25 seconds
        
        When Top action is Filters
        And  Plus menu is "Switching -> Marktbericht Taken"
