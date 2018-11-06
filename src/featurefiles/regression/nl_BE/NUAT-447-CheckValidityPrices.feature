@DWP
@REGRESSION
@B2B
@BUSINESS-DESK
    @J
Feature: NUAT-447: Check validity prices

    Background:
        Given   I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Check validity prices
        When B2B signed quote by customer "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Left menu is contracting-switching
#        And Click on link in View List at first row and "<string>" column polling 20 seconds
#        And
#        And Top action is Filters
#        And "B2C/B2B" selection is "B2B"
#        And "Type klant" selection is "Klant"
#        And "Naam" input is "steve"
#        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
#
#        When Dashboard menu is Contracten
#        And Change amount for a customer
#        And Contract plus and "Voorschotbedrag aanpassen"
#        And Amount values is 125
#        Then Changes are confirmed
#
#        When Contract plus and "Bekijk voorschotdata"
#        Then Amount of a customer value
