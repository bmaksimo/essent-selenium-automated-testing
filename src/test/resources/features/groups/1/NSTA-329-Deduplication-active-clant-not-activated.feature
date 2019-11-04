@REGRESSION
@DWP
@B2C
@ALL
@Unstable

Feature: NSTA-329 Deduplication activated customer

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-329
    Scenario: From de-duplication of client

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

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Electricity EAN code is "random"
        And "Startdatum" date is "1 months before now"
        And "Type aansluiting" selection is "YMR"
        And "Meternummer" input is "1000"
        And Option "test" "is" "On"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Quote is signed
        And Quote is signed in "Kontich"
        When Quote is confirmed

        When Dashboard menu is "Contracten"
        And  "1st" List element with value at column "EAN-code" is checked
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        When Click on top menu button UP
        Then Left menu is "sales-marketing"

        #Step 2: should deduplicate customer
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"
        When "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is duplicated
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       |         |         |
        Then Deduplication dialogue "Soortgelijke klanten" is shown
        And Deduplication dialogue link "Create quote for account" is clicked
        And Save changes

        Given "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed

        When "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed

        When Field "Street" input is "Mechelsesteenweg"
        And Field "Housenumber" input is "2"
        And Field "Postalcode" input is "2550"
        And Field "City" input is "Kontich"

        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Startdatum" date is "2 months from now"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Getekend document is uploaded
        And "Plaats ondertekening" input is "Kontich"
        When Quote is confirmed

        When Dashboard menu is "Contracten"
        Then Two contracts are displayed

        When Dashboard menu is "Details"
        Then There is one billing customer
