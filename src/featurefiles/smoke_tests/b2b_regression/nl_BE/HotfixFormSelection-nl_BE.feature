@_SMOKE
Feature: Basic elements - Hotfix NSTA-213, Form Selection by text

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario:
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
