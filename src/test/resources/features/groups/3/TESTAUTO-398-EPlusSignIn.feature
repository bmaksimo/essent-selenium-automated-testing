@REGRESSION
@DWP
@B2C
@REGRESSION
@ALL
    
Feature: TESTAUTO-398 E plus sign in

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-398
    Scenario: E plus sign in
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

        When "Pakket" selection is "flixheat"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When EAN code is generated
        And "Startdatum" date is "5 day before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "1000"
        And Options "test" "are" "On"

        And EAN code for gas is generated
        And Start datum is "5 day before now"
        And EAN-code input is "parameter:EAN-code-gas"
        And Meternummer input is "1000"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Click on "Selecteer pakket en producten" on create quote page
        And Click on "Overzicht en ondertekenopties" on create quote page
        And Quote is confirmed

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        Then "2nd" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        And Get Start Date
        Then Click on link in View List at "1st" row and "Contractnummer" column polling 60 seconds

        And Table "Contractlijnen" contains value "E+ service" at column "Producttype" retrying 5 times
        And Table contains matching data on given columns:
            | Table name     | Status & Product      | Producttype   |
            | Contractlijnen | Actief - Wait to send | E Plus Product|
        And Number of contract lines is "4"
        Then Start dates are same for all contract lines as contract start date "parameter:startDate"

        When Dashboard menu is "Service"
        Then Table "Interacties" contains value "Document Outbound document: CONF_CONTRACT_SALES_TC1_B2C" at column "Type & Onderwerp" retrying 5 times
        And Table "Interacties" contains value "Document Outbound document: Activation Letter" at column "Type & Onderwerp" retrying 5 times

        When Click on link in View List at "1st" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        Then EMC ID is present

        When Dashboard menu is "Service"
        When Click on link in View List at "2nd" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        Then EMC ID is present
