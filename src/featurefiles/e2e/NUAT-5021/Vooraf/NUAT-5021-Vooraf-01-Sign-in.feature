@DWP
@B2C
Feature: NUAT-5021 Voraaf Step 1. Sign-in on vooraf (prepaid)

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NUAT-5021-VOORAF-1
    Scenario: Sign-in on Vooraf (prepaid)
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vooraf"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "2 weeks before now"
        And Options "test" are On
        And "Marktbericht" selection is "Volledig marktbericht"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And EAN code is generated
        And Gas EAN-code input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Wait for 10 seconds
        And Value at "Bedrag Vooraf (incl. btw)" in the card "Elektriciteit Vooraf" is "800 €"
        And Value at "Bedrag Vooraf (incl. btw)" in the card "Aardgas Vooraf" is "900 €"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And Quote is signed
        And "Datum ondertekening" date is "2 weeks before now"
        And Quote is signed in "Kontich"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"
