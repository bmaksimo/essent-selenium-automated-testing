@ALL
@E2E
@DWP
@REGRESSION
Feature: NUAT-5021 Complete scenario from de-duplication of client with guarantee to inactive client

    @NUAT-5021
    Scenario: From de-duplication of client to inactive client via passive renewal

        # Step 1: create customer with guarantee
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
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

        And EAN code is generated
        And "Startdatum" date is "now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Quote is confirmed
        And "1st" list element has cell value "Sales Verstuurd naar de klant - Geaccepteerd" at column "Type & status"

        When Top arrow button is "Up"
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given "1st" List element with value at column "Klantnummer & Naam" is checked
        Then  External status is "On" for SuiteCRM Customer Number "parameter:Klantnummer & Naam"

        #Step 2: should deduplicate customer
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When B2C sales channel is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        Given Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer is duplicated
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
        And "Startdatum" date is "now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Plaats ondertekening" input is "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote for account is signed
        When Quote for account is confirmed
        And "1st" list element has cell value "Sales Getekend - Waarborg" at column "Type & status"

        When Dashboard menu is "Marktberichten"
        Then View List is empty

        # Step 3 - Should create guarantee invoice
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column
        And Dashboard menu is "Billing"
        Then "1st" list element has cell value "Invoice (GUARANTEE)" at column "ID & Type" polling 500 seconds

        # 3 - Download CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:Klantnummer & Naam"
        When Column "Account Number" with value "parameter:Klantnummer & Naam" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the first row with "Amount receivable" is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded

        # 4 - import and match CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report contains success string "Number of statements processed : 1"

        #Switch back to Dwp and verify Guarantee Payment
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"

        And Click on link in View List at "1st" row and "Klantnummer & Naam" column
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "equal to 0"

        #Step 5 - Should create Supplier Switch market message
        When Dashboard menu is "Marktberichten"
        Then "1st" list element has cell value "Supplier Switch" at column "Module & Label" polling 450 seconds
