@DWP
@REGRESSION
@B2B
@SERVICE-CONTRACTING
@NOREG04
Feature: NUAT-447: Check validity prices

    Background:
        Given   I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NUAT-447
    Scenario: Check validity prices
        When B2B signed quote by customer "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Left menu is "contracting-switching"
        And Top menu item is "Offertes"
        And Filter button is clicked
        And Label "Type offerte" is "Sales"
        And Label "Status" is "Verstuurd naar klant"
        And Take Offertenummer from first offerte
        And Plus action and "Handtekening ontvangen" of first customer from list waiting for 30 seconds
        And "Datum ondertekening" date is "now"

        Then Changes are confirmed waiting for 5 seconds

        When Reset filter
        And "Offertenummer" input is "parameter:offertenummer"
        And Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column polling 600 seconds
        And Click on top menu button PLUS and navigate to "Offertes -> Check geldigheid tarieven"
        And Form header is "Updated Prices"
        And "Ja, ik wil de offerte met de nieuwe tarieven goedkeuren" turn on with dot
        And Changes are confirmed waiting for 5 seconds
        Then Bevestigen

        When Plus action of "1" element from "TasksOnQuotes" and click on Mark As Done/Markeren Als Verwerkt
        And Oplossing text is "ja"
        Then Changes are confirmed

        When Click on top menu button PLUS and navigate to "Offertes -> Status - getekend"
        And Sign quote file is uploaded
        And Changes are confirmed waiting for 5 seconds
        Then Offerte status is "Getekend"
