@DWP
@B2B
@REGRESSION
@ALL

Feature: TESTAUTO-32: Price validity check on an Elec UP quote

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-32
    Scenario: Price validity check on an Elec UP quote
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Contract without signature is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Sales"
        Then Table "Offertes" contains value "Verstuurd naar de klant - Geaccepteerd" at column "Type & status"

        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum handtekening ontvangen" date is "now"
        And Option "Taak aanmaken voor de manager?" "is" "On"
        And Changes are confirmed
        Then Table "Offertes" contains value "Handtekening ontvangen - Geaccepteerd" at column "Type & status"

        When Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column polling 30 seconds
        Then Table "Taken" contains value "Check quote" at column "Naam & Type & Subtype"
        Then Table "Taken" contains value "quotation - quote_price_validity" at column "Naam & Type & Subtype"

        When Plus menu is "Offertes -> Check geldigheid tarieven"
        And Form header is "Updated Prices"
        And "Nieuwe tariefdatum" date is "now"
        And Option "Ja, ik wil de offerte met de nieuwe tarieven goedkeuren." "is" "On"
        And Changes are confirmed
        And Bevestigen
        And Sleep for 20 seconds
        Then Table "Offertelijnen" contains value "Handtekening ontvangen" at column "Status & Product"
        Then Table "Offertelijnen" contains value "Afgekeurd" at column "Status & Product"

        When Plus action of "1" element from "Quotelines" and click on "Bekijk details tarief"
        And Sum of Rate for signature received is "High"
        And Sum of Rate for signature received is "Low"
        Then Clicked on sign X

        When Plus action of "2" element from "Quotelines" and click on "Bekijk details tarief"
        Then "parameter:sumRatesHighSignature" and "parameter:sumRatesLowSignature" equals Sum of High&Low rates for rejected rates
