@E2E
@DWP
    Feature: NUAT-5021 Complete scenario from de-duplication of client with guarantee to inactive client
     @NUAT-5021
     Scenario: From de-duplication of client to inactive client via passive renewal

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "now"
        And Electricity EAN code is "random"
        #Uncomment for "move in" flow
        #And Switch type is Move in
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"


        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then 1st list element has cell value Sales Getekend - Geaccepteerd at column Type & status

        When Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And  1st List element with value at column "EAN-code" is checked
        And  1st list element has cell value Actief at column "Contractnummer" polling 450 seconds

        When Top arrow button is Up
        And Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given 1st List element with value at column "Klantnummer & Naam" is checked
        Then  External status is "On" for SuiteCRM Customer Number "parameter:Klantnummer & Naam"

