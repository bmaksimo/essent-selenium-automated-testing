@REGRESSION
@DWP
@B2C
@REGRESSION
@NOREG04

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
        Then Quote is confirmed

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 600 seconds

        When Click on link in View List at "1st" row and "Contractnummer" column waiting for 60 seconds
        And Click on Plus action of table "Contractlijnen" at row where "PRODUCTTYPE" is "E Plus Product" and click on "Bekijk alle fulfilment records"
        And Plus action of "1" element from "fulfilment" and click on "Bewerken"
        And "Status" selection is "Delivered"
        And "Leveringsdatum" date is "5 day before now"
        And Changes are confirmed waiting for 5 seconds
        Then Table contains matching data on given columns:
            | Table name     | Status & Product   | Producttype   |
            | Contractlijnen | Actief - Delivered | E Plus Product|

        When Dashboard menu is "Marktberichten"
        And Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Search by "parameter:EAN-code-generated"
        And Changes are confirmed
        And "Module" selection is "INITIATE LEAVING CUSTOMER"
        And "Label" selection is "Without Handover Document "
        And Option "Testing?" is "On"
        And Changes are confirmed waiting for 5 seconds
        And "1st" list element has cell value "INITIATE LEAVING CUSTOMER" at column "Module & Label" polling 120 seconds
        Then "1st" list element has cell value "Geaccepteerd" at column "Status & ED" polling 240 seconds

        When Dashboard menu is "Service"
        And Table "Taken" contains value "check rest value" at column "Naam & Type & Subtype" retrying 5 times
        And Click on list Item on table "TasksOnAccount" where Status is "Open"
        Then Positive amount is verified
    

