@B2B_REGRESSION
Feature: Dwp test for checking usage of a customer

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

        Scenario:
            When Left Menu Item is sales-marketing
            And Top Menu Item is Klanten
            And Top Action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Naam" input is "steve"
            Then Click on link in View List at 1st row and "Klantnummer & Naam" column

            When Plus Menu is "Billing -> Verbruiken voor klant"
            And View List Header is "Verbruiken"
            Then View list is not empty
