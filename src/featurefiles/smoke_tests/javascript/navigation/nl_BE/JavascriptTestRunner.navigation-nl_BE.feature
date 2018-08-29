@SMOKE
Feature: Javascript DWP testing

    Background:
        Given I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario: We can access the main gui elements
        When Left menu is contracting-switching
        And Top Menu Item is Marktberichten
        Then View List Header is "Marktberichten" appears within 25 seconds

        When Click on link in View List at 1st row and "Klant & EAN-code" column
        And  Top Arrow button is Up
        When Click on link in View List at 1st row and "Klant & EAN-code" column
        And  Dashboard menu is Contracten
        And  Top Arrow button is Up
        And  Plus Menu is "Switching -> Marktbericht Taken"
        Then View List Header is "Taken Marktberichten" appears within 25 seconds
        And  Find web element by Xpath "//div[@class='top-menu']/sub-menu/sub-menu-link[@label='Marktberichten']/a"

        When Top Action is Filters
        And  Top Action is Plus Menu
        And  Top Action is Filters
        And  Top Action is Plus Menu
