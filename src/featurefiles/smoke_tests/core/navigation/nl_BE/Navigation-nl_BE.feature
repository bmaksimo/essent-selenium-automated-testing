@DWP
@SMOKE
Feature: DWP Gui navigation  elements

    Background:
        Given I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario: Using DWp navigation elements (top, filter, plus- buttons)
        When Left menu is contracting-switching
        And  Top menu item is Marktberichten
        Then View list header is "Marktberichten" appears within 25 seconds

        When Click on link in View List at 1st row and "Klant & EAN-code" column
        And  Top arrow button is Up
        When Click on link in View List at 1st row and "Klant & EAN-code" column
        And  Dashboard menu is Contracten
        And  Top arrow button is Up
        And  Plus menu is "Switching -> Marktbericht Taken"
        Then View list header is "Taken Marktberichten" appears within 25 seconds

        When Top action is Filters
        And  Top action is Plus Menu
        And  Top action is Filters
        And  Top action is Plus Menu
