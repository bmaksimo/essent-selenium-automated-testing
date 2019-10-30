@REGRESSION
@DWP
@B2B
@PERFORMANCE
@ALL

Feature: TESTAUTO-120 UP B2B create quote flow online sign in

     Background:
        Given   I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-120
    Scenario: UP B2B create quote flow online sign in
        When Click on top menu button PLUS and navigate to "Sales -> UP/TK2 -> Creëer nieuwe offerte (B2B)"
        And Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
        And Value at "Klantacceptatie" in the card "Perform customer acceptance check" is "Geaccepteerd"
        And Customer acceptance checks page is confirmed
        Then Form header is "Contact person"
        And Field "First name" input is "parameter:contact-person-first-name"
        And Field "Last name" input is "parameter:contact-person-last-name"
        And Select Nace-Code
        And NaceCode in search is 01110 - Teelt van granen (m.u.v. rijst), peulgewassen en oliehoudende zaden
        And Company address is
            | street          | houseNr  | houseNrAdd | bus | postalCode | city     | country |
            | Mechelsesteenweg| 2        |            |     | 2550       | Kontich  |         |

        And  "E-mailadres" input is "test@test.com"
        And  "Telefoon" input is "+32 2 545 67 89"
        And Customer details are confirmed
        Then Form header is "Connection details"
        When "Producttype" selection is "Elektriciteit"
        And "Type aansluiting" selection is "YMR"
        And "Meternummer" input is "30301267ISK"
        And Option "test" "is" "On"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Pricing details"
        When "Tariefgroep" selection is "UP"
        And "Product" selection is "Elektriciteit Vast"
        And "Startdatum" date is "now"
        And "Einddatum" date is last day of current month next year
        And "Verbruik enkelvoudig (kWh)" input is "5000"
        And Bevestigen
        Then Form header is "Billing details"
        When "Betalingswijze" selection is "Overschrijving"
        Then Quote is confirmed

        When Dashboard menu is "Sales"
        And Plus action of "1" element from "QuotesOnAccount" and click on "Verzenden naar klant"
        And Option "Mij een e-mail sturen?" "is" "On"
        And Changes are confirmed
        Then Table "Offertes" contains value "Verstuurd naar de klant - Geaccepteerd" at column "Type & status" retrying 5 times

        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And Changes are confirmed
        Then Table "Offertes" contains value "Handtekening ontvangen - Geaccepteerd" at column "Type & status" retrying 5 times

        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        And Quote for account is signed online in modal
        And Changes are confirmed
        Then Table "Offertes" contains value "Getekend - Geaccepteerd" at column "Type & status" retrying 5 times

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 1500 seconds
