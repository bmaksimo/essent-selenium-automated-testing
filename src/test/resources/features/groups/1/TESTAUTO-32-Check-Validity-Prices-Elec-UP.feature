@DWP
@B2B
@REGRESSION
@ALL

Feature: TESTAUTO-32: Price validity check on an Elec UP quote

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-32
    Scenario: Price validity check on an Elec UP quote
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Contract without signature is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Sales"
        And Table "Offertes" contains value "Verstuurd naar de klant - Geaccepteerd" at column "Type & status" within 120 seconds

        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum handtekening ontvangen" date is "now"
        And Option "Taak aanmaken voor de manager?" "is" "On"
        And Changes are confirmed
        And Table "Offertes" contains value "Handtekening ontvangen - Geaccepteerd" at column "Type & status" within 60 seconds
        And Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column polling 60 seconds
        And Check table value "Check quote" is found for created quote
        And Check table value "quotation - quote_price_validity" is found for created quote
        And Click on top menu button PLUS and navigate to "Offertes -> Check geldigheid tarieven"
        And Form header is "Updated Prices"
        And "Nieuwe tariefdatum" date is "now"
        And Option "Ja, ik wil de offerte met de nieuwe tarieven goedkeuren." "is" "On"
        And Bevestigen

        Then Check table value "Handtekening ontvangen" is found for created quote
        And Check table value "Afgekeurd" is found for created quote
        And Plus action of "1" element from "Quotelines" and click on "Bekijk details tarief"
        And Sum of Rate for signature received is "High"
        And Sum of Rate for signature received is "Low"
        And Clicked on sign X
        And Plus action of "2" element from "Quotelines" and click on "Bekijk details tarief"
        And "parameter:sumRatesHighSignature" and "parameter:sumRatesLowSignature" equals Sum of High&Low rates for rejected rates
