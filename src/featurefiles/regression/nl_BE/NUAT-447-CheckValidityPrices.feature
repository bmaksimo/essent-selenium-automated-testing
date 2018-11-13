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
        And Top menu item is Offertes
        And I click on the filter button
        And Label "Type offerte" is "Sales"
        And Label "Status" is "Verstuurd naar klant"
#        And Take Offertenummer from firs offerte
        And Plus action and "Handtekening ontvangen" of first customer from list
        And "Taak aanmaken voor de manager" turn on
        Then Changes are confirmed

        When Reset filter
#        And Click on Offerte with "parameter:offertenummer"
        And  Click on link in View List at 1st row and "Nummer & Getekend contractnummer" column polling 20 seconds
        And Plus menu is "Offertes -> Check geldigheid tarieven"
        And "Ja, ik wil de offerte met de nieuwe tarieven goedkeuren." turn on
        Then Changes are confirmed

        When Plus menu is "Offertes -> Status - getekend"
        And Client signature file is uploaded








#        And Plus menu is "Offertes -> Check geldigheid tarieven"
#
#        When Click on link in View List at 1st row and "Nummer & Getekend contractnummer" column polling 20 seconds
#        And Plus menu is "Offertes -> Check geldigheid tarieven"

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
