@DWP
@REGRESSION
@CREDIT-AND-CONTROL
@UNSTABLE
Feature: NUAT-412 part: Create / import coda file

    @NUAT-412-2-STABILIZE
    Scenario: Create active contract TK1
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
        And Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
        And Value at "Klantacceptatie" in the card "Perform customer acceptance check" is "Geaccepteerd"
        And Customer acceptance checks page is confirmed
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Contact person"

        When "Rechtsvorm" selection is "bvba"
        And Select Nace-Code
        And NaceCode in search is 01120 - Teelt van rijst

        And Field "First name" input is "parameter:contact-person-first-name"
        And Field "Last name" input is "parameter:contact-person-last-name"

        And Company address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |


        And Company contact info is generated
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2B (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        #When "Startdatum" date on "Elektriciteit Vast" card is "35 days before now"
        When Option "test" is On
        And EAN code is generated
        And "Startdatum" date on "Elektriciteit Vast" card is "now"
        And "EAN-code" input on "Elektriciteit Vast" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        # 2 - invoice run advance
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:company-name"

        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at "1st" list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at "1st" list row
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is "Up"
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds










