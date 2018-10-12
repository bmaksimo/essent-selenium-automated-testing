@DWWP
@REGRESSION
Feature: End of contract via DWP - nl_BE version

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

        Scenario:
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "150715682"
            And Click on link in View List at 1st row and "Klantnummer & Naam" column
            Then Dashboard menu is Contracten

            When Find "Actief" contract
            And Dashboard menu is Marktberichten
            And Click on Start nieuw marktbericht
            And Click Select Contractline
            And Search for ean code
            Then Changes are confirmed

            When Input in Module is "INITIATE STOP ACCESS"
            And Input in Label is "Non-Residential End-of-Contract"
            And Check toggle "Testing"
            And Check toggle "Market mocK"
            And Changes are confirmed
            Then Confirm task was "Non-Residential End-of-Contract"
