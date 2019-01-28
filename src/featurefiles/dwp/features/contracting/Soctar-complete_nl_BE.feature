@DWP
@SOCTAR
Feature: NUAT-5019 Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @SOCTAR-01-03
    Scenario: Create Soctar (Social tarif) quote and contract, and check Soctar confirmation letter
        # 1 - GUI contract creation
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When  "Sales kanaal" selection is "Inbound"
        And "Tariefdatum" date is "2 weeks before now"
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

        When Electricity EAN code is "random"
        And Option "test" is On
        And Option "MM should respond?" is On
        And "Startdatum" date is "2 weeks before now"

        And Connection details are confirmed
        Then Form header is "Billing details"


        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "2 weeks before now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        When Top arrow button is "Up"
        And Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given "1st" List element with value at column "Klantnummer & Naam" is checked

            #Steps 2 and 3  - Soctar file sftp upload
        Given Soctar customer Id is "parameter:Klantnummer & Naam"
        And   Soctar EAN is "parameter:EAN-code"
        And   Soctar start date is "now"
        Then  Soctar file is uploaded to "/home/ESSENT/sa_sftpcrm_smx/data/soctar" remote directory

