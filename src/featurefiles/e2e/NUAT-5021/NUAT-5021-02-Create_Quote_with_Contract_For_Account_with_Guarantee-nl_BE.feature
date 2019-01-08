@DWP
@B2C
Feature: NUAT-5021 Step 2. Deduplicate account

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    @NUAT-5021-STEP-2
    @CREATE-QUOTE-FOR-ACCOUNT
    Scenario: Trigger Deduplicate process

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form header is "Personal details"

        Given Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And "Aanspreking" selection is "Mevr."
        And "Voornaam" input is "Randa"
        And "Familienaam" input is "Gomez"
        And "E-mailadres" input is "randa.gomez@example.com"
        And "Gsm-nummer" input is "+31683687691"
        And "Geboortedatum" date is "04/03/1958"
        And  Deduplication dialogue "Soortgelijke klanten" is shown
        And  Deduplication dialogue link "Create quote for account" is clicked
        Then Form header is "Quote details"

        When "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Select package & fuel type"

        When "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Field "Street" input is "Mechelsesteenweg"
        And Field "Housenumber" input is "2"
        And Field "Postalcode" input is "2550"
        And Field "City" input is "Kontich"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And "Plaats ondertekening" input is "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote for account is signed
        When Quote for account is confirmed
        Then View list header is "Offertes"
        And 1st list element has cell value Sales Getekend - Waarborg at column Type & status
