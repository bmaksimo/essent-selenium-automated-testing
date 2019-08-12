@REGRESSION
@DWP
@B2C
@ALL

Feature: NSTA - 337 Move old address - Elec

Background:
    Given   I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-120
    Scenario: UP B2B create quote flow online signin
        When Plus menu is "Sales -> UP/TK2 -> Creëer nieuwe offerte (B2B)"
#        Then Form header is "Perform customer acceptance check"
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
            | street                  | houseNr  | houseNrAdd | bus | postalCode | city                 | country |
            | Fernand Brunfautstraat  | 65       |            |     | 1080       | Sint-Jans-Molenbeek  |         |

        And  "E-mailadres" input is "test@test.com"
        And  "Telefoon" input is "+32 2 545 67 89"
        And Customer details are confirmed
        Then Form header is "Connection details"
        When "Producttype" selection is "Elektriciteit"
        And "Type aansluiting" selection is "YMR"
#        And "Meter type" selection is "YMR"
        And "Meternummer" input is "30301267ISK"
        And Option "test" "is" "On"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Pricing details"
        And "Tariefgroep" selection is "UP"
        And "Product" selection is "Elektriciteit Vast"
        And "Startdatum" date is "5 day before now"
        And "Einddatum" date is "1 year from now"
        And "Verbruik enkelvoudig (kWh)" input is "5000"
        And Bevestigen
#        And Pricing details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Quote is confirmed

        #2.1 Sign quote
        When Dashboard menu is "Sales"
        And Plus action of "1" element from "QuotesOnAccount" and click on "Verzenden naar klant"
        And Option "Mij een e-mail sturen?" "is" "on"
        And Changes are confirmed
        Then Quote status is "Verstuurd naar de klant - Geaccepteerd"
        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And Set Signature received is confirmed
        Then Quote status is "Handtekening ontvangen - Geaccepteerd"

        When Plus action of "1" element from "QuotesOnAccount" and Click on "Bevestig"
        And Client signature file is uploaded
        And Quote is signed
        Then Quote status is "Getekend - Geaccepteerd"
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
