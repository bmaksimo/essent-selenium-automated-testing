@DWP
@CORE
Feature: GUI navigation elements.

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @CORE-NAVIGATION
    Scenario: Using DWP GUI navigation elements: top-, filter-, plus- buttons.
        When Left menu is "contracting-switching"
        And Top menu item is "Marktberichten"

        When Click on link in View List at "1st" row and "Klant & EAN-code" column
        And Click on top menu button UP
        When Click on link in View List at "1st" row and "Klant & EAN-code" column
        And Dashboard menu is "Contracten"
        And Click on top menu button UP
        And Click on top menu button PLUS and navigate to "Switching -> Marktbericht Taken"

        When Top action is "Filters"
        And Top action is "Plus Menu"
        And Top action is "Filters"
        And Top action is "Plus Menu"
