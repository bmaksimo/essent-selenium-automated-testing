@B2B_REGRESSION
Feature: Dwp test for changing amount for a customer

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Naam" input is "steve"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 5 seconds
        
        When Dashboard menu is Contracten
        And Change amount for a customer
        And Contract plus and "Voorschotbedrag aanpassen"
        And Amount values is 24
        Then Changes are confirmed

        When Contract plus and "Bekijk voorschotdata"
        Then Amount of a customer value
