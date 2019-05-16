@DWP
@SALES-MARKETING
@REGRESSION
@B2C
@ALL
Feature: NSTA-327: Creating a B2C Quote TC1 with "Supplier Switch" without using Market Mock.

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-327
    @SUPPLIER-SWITCH-NO-MM
    Scenario: Create a B2C Account with Quote, With supplier switch, without using Market Mock

        #Step 1. Selecteer type Offerte / Select Quote type
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        #Step 2. Fill in the Customer details
        #To be: Make sure the address you fill in isn’t already contracted (active/inactive) in NOVA.
        #Is: using random EAN + Mechelsesteenweg 2, 2550 Kontich
        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        #Step 3. Select package and fuel type
        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed

        #Step 4. Fill in connection information
        When "Startdatum" date is "1 month before now"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        #And Option "Is de meter geopend?" is Off

        When Connection details are confirmed
        Then Form header is "Billing details"

        #Step 5. Fill in billing information
        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        #Step 6. Overview and signature details
        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "2 weeks before now"
        And Quote is confirmed

        #Step 7. Check quote
        When View list header is "Offertes"
        And "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        #Step 8. Check contracts
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" list element has cell value "Te activeren" at column "Contractnummer"
        And "1st" list element has cell value "parameter:EAN-code-generated" at column "EAN-code"
        And "1st" List element with value at column "Start & Einddatum" is checked

        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds

        #Step 9. Check market messages
        When Dashboard menu is "Marktberichten"
        Then View list header is "Marktberichten"
        And "1st" list element has cell value "START ACCESS Supplier Switch" at column "Module & Label" polling 450 seconds
        And "1st" list element has cell value "parameter:Start & Einddatum" at column "Status & ED" polling 120 seconds
        And "1st" list element has cell value "Gesloten" at column "Status & ED" polling 120 seconds

