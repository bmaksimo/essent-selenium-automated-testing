@DWP
@B2C
Feature: NSTA-330 Voraaf Step 1. Sign-in on vooraf (prepaid)

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-330-VOORAF
    Scenario: NSTA-330 Voraaf Step 1. Sign-in on vooraf (prepaid)
        #Sign in, bi-fuel contract
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

        #When "Startdatum" date is "2 weeks before now"
        When Options "test" are On
        And "Marktbericht" selection is "Volledig marktbericht"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And EAN code is generated
        And Gas EAN-code input in the "Aardgas" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
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

        #Check "vooraf prepaid" contract lines
        When Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column
        And "Kortingen op de offerte" list is empty
        And Table "Offertelijnen" contains cell value "Getekend TK1-Aardgas Vooraf (TC_VOORAF_B2C)" at column "Status & Product" on "1st" row
        And Table "Offertelijnen" contains cell value "Getekend TK1-Elektriciteit Vooraf (TC_VOORAF_B2C)" at column "Status & Product" on "2nd" row

        And Dashboard menu is "Contracten"
        And "1st" list element has cell value "Wacht op startdatum" at column "Contractnummer" polling 500 seconds
        And Table "Actieve en toekomstige connecties" contains cell value "Wacht op startdatum" at column "Contractnummer" on "2nd" row
        And Click on link in "Contracten" View List at "1st" row and "Nummer & Aanmaakdatum" column
        And Table "Contractlijnen" contains cell value "Wacht op startdatum TK1-Elektriciteit Vooraf (TC_VOORAF_B2C)" at column "Status & Product" on "1st" row
        And Table "Contractlijnen" contains cell value "Wacht op startdatum TK1-Aardgas Vooraf (TC_VOORAF_B2C)" at column "Status & Product" on "2nd" row


        #And Dashboard menu is "Service"

