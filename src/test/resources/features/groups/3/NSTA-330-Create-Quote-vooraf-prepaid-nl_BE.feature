@ALL
@DWP
@B2C
@REGRESSION

Feature: NSTA-330. Check the generation of prepaid advance invoice.
         Sign-in a new customer with TC1 quote with electricity and gas prepaid products.
         Check prepaid advance invoice total amount as sum of electricity and gas advance amounts.
         Check invoice due date, should be 19 days after transaction date.
    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-330
    Scenario: Sign-in on Vooraf (prepaid)
        #1. Sign-in a new customer with TC1 quote with electricity and gas prepaid products.
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 1       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vooraf"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        And Start date in cards is "35 days before now"
        And Options "test" "are" "On"

        And EAN code is generated
        And EAN in card Electricity is "parameter:EAN-code-generated"
        And EAN code is generated
        And EAN in card Gas is "parameter:EAN-code-generated"
        And Add customer address again if not populated first time
            | street           | houseNr | postalCode | city    |
            | Mechelsesteenweg | 1       | 2550       | Kontich |
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Numeric value at "Bedrag Vooraf (incl. btw)" in the card "Elektriciteit Vooraf" is "greater than 0"
        And Numeric value at "Bedrag Vooraf (incl. btw)" in the card "Aardgas Vooraf" is "greater than 0"
        And Prepaid advance amounts are collected as numbers
            | cardName             | fieldName                 | parameterName    |
            | Elektriciteit Vooraf | Bedrag Vooraf (incl. btw) | bedrag-vooraf-el |
            | Aardgas Vooraf       | Bedrag Vooraf (incl. btw) | bedrag-vooraf-gas|

        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And Quote is signed
        And "Datum ondertekening" date is "35 days before now"
        And Quote is signed in "Kontich"
        And Quote is confirmed
        And Dashboard menu is "Contracten"
        And Dashboard menu is "Sales"
        Then Table "Offertes" contains value "Sales Getekend - Geaccepteerd" at column "Type & status" retrying 30 times

        When Click on link in View List at "1st" row and "Nummer & Getekend contractnummer" column
        And "Kortingen op de offerte" list is empty
        Then Table "Offertelijnen" contains value "Getekend TK1-Aardgas Vooraf (TC_VOORAF_B2C)" at column "Status & Product" retrying 30 times
        And Table "Offertelijnen" contains value "Getekend TK1-Elektriciteit Vooraf (TC_VOORAF_B2C)" at column "Status & Product" retrying 30 times

        When Dashboard menu is "Contracten"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds
        And "2nd" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds
        And Click on link in "Contracten" View List at "1st" row and "Nummer & Aanmaakdatum" column
        Then Table "Contractlijnen" contains value "Actief TK1-Aardgas Vooraf (TC_VOORAF_B2C)" at column "Status & Product" retrying 30 times
        And Table "Contractlijnen" contains value "Actief TK1-Elektriciteit Vooraf (TC_VOORAF_B2C)" at column "Status & Product" retrying 30 times

        #2.1 Check prepaid advance invoice total amount as sum of electricity and gas advance amounts.
        When Dashboard menu is "Service"
        Then List element matching value "Outbound document: prepaidadvance" at column "Type & Onderwerp" from table "Interacties" is checked

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (PREPAIDADVANCE)" at column "ID & Type" retrying 30 times
        And  "1st" element of table "Transacties" at currency column "Bedrag" is sum of
        |parameter:bedrag-vooraf-el |
        |parameter:bedrag-vooraf-gas|

        #2.2 Check invoice due date, should be 19 days after transaction date.
        And "1st" list element with date interval at column "Datum & Vervaldatum" from table "Transacties" is "19 days"
