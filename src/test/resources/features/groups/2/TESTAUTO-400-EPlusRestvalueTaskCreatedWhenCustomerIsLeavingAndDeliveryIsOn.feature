@REGRESSION
@DWP
@B2C
@REGRESSION
@ALL


Feature: TESTAUTO-400 E plus restvalue task created when customer is leaving and delivery is on

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-400
    Scenario: E plus restvalue task created when customer is leaving and delivery is on
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When "Pakket" selection is "flixmood"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When EAN code is generated
        And "Startdatum" date is "5 day before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "1000"
        And Options "test" "are" "On"

        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

#        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
#        When Left menu is "sales-marketing"
#        And Top menu item is "Klanten"
#        And Top action is Filter from "sales-marketing" menu retrying 5 times
#        And "Klantnummer" input is "1001337682"
#        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Contracten"
        When Click on link in View List at "1st" row and "Contractnummer" column waiting for 1 seconds
        And Click on Plus action of table "Contractlijnen" at row where "PRODUCTTYPE" is "E Plus Product" and click on "Bekijk alle fulfilment records"
#        And Plus action of "1" element from "ContractlinesOnContract" and click on "Bekijk alle fulfilment records"
        And Plus action of "1" element from "fulfilment" and click on "Bewerken"
        And "Status" selection is "Delivered"
        And "Leveringsdatum" date is "5 day before now"
        And Changes are confirmed
        And Table contains matching data on given columns:
            | Table name     | Status & Product   | Producttype   |
            | Contractlijnen | Actief - Delivered | E Plus Product|
    

