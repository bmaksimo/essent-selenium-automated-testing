@DWP
@REGRESSION
@B2B
@BUSINESS-DESK
Feature: NUAT-448: Change The Advance Amount For A Customer - nl_BE

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Change amount for a customer
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is Contracten
        And Change amount for a customer
        And Contract plus and "Voorschotbedrag aanpassen"
        And Amount values is 125
        Then Changes are confirmed

        When Contract plus and "Bekijk voorschotdata"
        Then Amount of a customer value
