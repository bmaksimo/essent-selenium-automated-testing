@DWP
@REGRESSION
Feature: Dwp test for checking usage of a customer

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

        Scenario: Checking usage of a customer
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
            And Top action is Filters
            And "Klantnummer" input is "parameter:accountNumber"
            Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

            When Plus menu is "Billing -> Verbruiken voor klant"
            And View list header is "Verbruiken"
            Then View list is not empty
