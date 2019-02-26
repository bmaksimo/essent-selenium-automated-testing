@DWP
@B2C
@REGRESSION
@SOCTAR-CONFIRMATION


Feature: NSTA 332 Soctar confirmation --> Manual
    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    #Step 1: Create active contract
    @ONBOARDING
    Scenario: Create a B2C Quote, YMR metered, Is Residential checked, Vast, market mock, start date in past

#        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
#        Then Form header is "Quote details"
#
#        When "Tariefdatum" date is "now"
#        And "Sales kanaal" selection is "Inbound"
#        And Quote details are confirmed
#        Then Form header is "Personal details"
#
#        When Customer is random
#        And Customer address is
#            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
#            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
#        And Customer details are confirmed
#        Then Form header is "Select package & fuel type"
#
#        When Package is "Vast"
#        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
#        And Package and Fuel Type is confirmed
#        Then Form header is "Connection details"
#
#        When EAN code is generated
#        And "EAN-code" input is "parameter:EAN-code-generated"
#        And Electricity market mock test is Open
#        And "Startdatum" date is "now"
#        And Connection details are confirmed
#        Then Form header is "Billing details"
#
#        When "Betalingswijze" selection is "Overschrijving"
#        And Billing details are confirmed
#        Then  Form header is "Quote overview"
#
#        When Option "Heeft de klant al getekend?" is On
#        And "Kanaal ondertekening" selection is "Papier"
#        And "Plaats ondertekening" input is "Kontich"
#        And "Datum ondertekening" date is "now"
#        And Quote is signed
#        When Quote is confirmed
#        Then View list header is "Offertes"
#        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"
#
#        When Dashboard menu is "Contracten"
#        Then View list header is "Actieve en toekomstige connecties"
#        And  "1st" List element with value at column "EAN-code" is checked
#        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2C"
        And "Klantnummer" input is "1000098705"
#       1000055619
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds


     #Step 2: Change contract to SOCTAR
       When Dashboard menu is "Contracten"
       Then Get Start Date
       When Plus action of "1" element from "ContractsOnAccount" and click on "TK1 Soctar Productwijziging "
#       Then Get Attest Date
       And "Datum attest" date is "1 month from now"
       Then Populate Soctar with dates "parameter:startDate" and "parameter:inputValue"
       And "Startdatum nieuwe offerte" date is "parameter:StartDateByQuarter"
       And "Einddatum nieuw contractvoorstel" date is "parameter:EndDateByYear"
       Then Changes are confirmed
       Then Bevestigen


     #Step 3: Check SOCTAR product change
       When Dashboard menu is "Contracten"
       And "Actieve en toekomstige connecties" list is not empty
       And Table "Contracten" contains value "Geannuleerd (Geaccepteerd)" at column "Type & status"
       And Table "Contracten" contains value "Verwerkt (Geaccepteerd)" at column "Type & status"
       And Table "Actieve en toekomstige connecties" contains value "Actief" at column "Contractnummer"
       And Table "Actieve en toekomstige connecties" contains value "sociaal tarief (SOCTAR)" at column "EAN-code"


