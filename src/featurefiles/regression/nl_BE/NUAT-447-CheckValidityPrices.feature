@DWP
@REGRESSION
@B2B
@SERVICE-CONTRACTING
Feature: NUAT-447: Check validity prices

    Background:
        Given   I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    Scenario: Check validity prices
        When B2B signed quote by customer "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Left menu is "contracting-switching"
        And Top menu item is "Offertes"
        And Filter button is clicked
        And Label "Type offerte" is "Sales"
        And Label "Status" is "Verstuurd naar klant"
        And Take Offertenummer from first offerte
        And Plus action and "Handtekening ontvangen" of first customer from list
        And Client signature receive data is "01/11/2016"
        And "Taak aanmaken voor de manager" turn on
        Then Changes are confirmed

        When Reset filter
        And Offertenummer input is "parameter:offertenummer"
        And Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column polling 20 seconds
        And Plus menu is "Offertes -> Check geldigheid tarieven"
        And "Ja, ik wil de offerte met de nieuwe tarieven goedkeuren" turn on with dot
        Then Bevestigen

        When Plus action of "1" element from "TasksOnQuotes" and click on Mark As Done/Markeren Als Verwerkt
        And Oplossing text is "ja"
        Then Changes are confirmed

        When Plus menu is "Offertes -> Status - getekend"
        And Sign quote file is uploaded
        And Changes are confirmed
        Then Offerte status is "Getekend"
