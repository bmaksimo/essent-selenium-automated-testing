@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-448: Change The Advance Amount For A Customer - nl_BE

    Background:
        Given   I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-448
    Scenario: Change amount for a customer
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        Given B2B Active Contract is
            | productType | isFakeAddress | switchType      | meterType | kwMax |
            | TC1         | FAKE          | SUPPLIER SWITCH | YMR       | 50000 |
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 40 seconds

        When Dashboard menu is "Contracten"
        And Change amount for a customer
        And Contract plus and "Voorschotbedrag aanpassen"
        And Amount values is "125"
        And "Betalingsfrequentie" selection is "Maandelijks"
        Then Changes are confirmed
        And Sleep for 10 seconds

        When Contract plus and "Bekijk voorschotdata"
        Then Amount of a customer value
