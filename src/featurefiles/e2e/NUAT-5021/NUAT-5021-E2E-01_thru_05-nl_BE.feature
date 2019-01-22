@DWP
@ODOO
@B2C
Feature: NUAT-5021 Complete scenario from de-duplication of client with guarantee to inactive client

    @NUAT-5021-01-05
    Scenario: From de-duplication of client to inactive client via passive renewal

        # Step 1: create customer with guarantee
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is "Inbound"
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

        When "Startdatum" date is "now"
        And Electricity EAN code is "random"
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"

        When Payment details are: method "Overschrijving", random IBAN, bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And  "1st" List element with value at column "EAN-code" is checked

        When Top arrow button is "Up"
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given "1st" List element with value at column "Klantnummer & Naam" is checked
        Then External status is "On" for SuiteCRM Customer Number "parameter:Klantnummer & Naam"

        #Step 2: should deduplicate customer
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Plus menu is "Sales -> Creëer nieuwe offerte  (TC1)"
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
        And EAN-code autocomplete value from the "1st" row is checked
        And "EAN-code" input is "parameter:EAN-code"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Advance frequency" selection is "Maandelijks"
        And "IBAN" input is "parameter:iban"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Datum ondertekening" date is "now"
        And "Plaats ondertekening" input is "Kontich"
        And Quote for account is signed
        When Quote for account is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Waarborg" at column "Type & status"

        # Step 3 - Should create guarantee invoice
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column
        And Dashboard menu is "Billing"
        Then "1st" list element has cell value "Invoice (GUARANTEE)" at column "ID & Type"

        # Step 4 - Generate Odoo CODA for account
        Given I renew login to Odoo as "t.geets"
        And Cleanup Odoo CODA files
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:Klantnummer & Naam"
        When Column "Account Number" with value "parameter:Klantnummer & Naam" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the "1st" row is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Modal button "Close" is clicked

        #Pay guarantee amount
        When Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        When CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        Then Odoo file import report
        When Modal button "View Bank Statement" is clicked
        And Wait for 30 seconds
        And Column "Reference" of the "1st" row is clicked
        Then Bank Statement "Close" button is clicked

        #Switch back to Dwp and verify Guarantee Payment
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"

        And Click on "parameter:Klantnummer & Naam"
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"
