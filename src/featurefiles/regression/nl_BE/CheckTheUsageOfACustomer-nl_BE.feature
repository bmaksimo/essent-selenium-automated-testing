@DWP
Feature: Dwp test for checking usage of a customer

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

        Scenario: Checking usage of a customer
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

            When Plus menu is "Billing -> Verbruiken voor klant"
            And View list header is "Verbruiken"
            Then View list is not empty
