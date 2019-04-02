@DWP
@ODOO
Feature: NUAT-5019 Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"
    @NUAT-5019-01-07
    Scenario: Create active contract that after dunning the contract becomes inactive

        # 1 - GUI contract creation
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
        | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
        | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        #Reg04  workaround  - Tariefkaart selection is commented out
#        When "Tariefkaart" selection is "TC_02_2019_B2C"
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        And EAN code is generated
        And "Startdatum" date is "now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And  Option "test" is On
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
        And "Naam" input is "parameter:suitecrm-customer-name"

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


        # 3 - Download CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
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

        #And Modal button "View Bank Statement" is clicked
        #And Wait for 30 seconds
        #When Column "Reference" of the "1st" row is clicked
        #And Bank Statement "Close" button is clicked


        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        When Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"

        # 5 - Create consumptions
        Given Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated until "1 year from now"
        #And Click on "parameter:EAN-code" link
        #And Consumption is available at "1st" row in "Van - Aan" column

        # 6 - Run mediation
        When Top arrow button is "Up"
        And Left menu is "billing"
        And Top menu item is "Klanten"
        And Plus menu is "Billing -> Start mediationrun"
        Then Modal dialog is "Start mediationrun"

        Given "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Datum afrekeningsfactuur" date is "parameter:billrun-date"
        Then Form is submitted

        # 7 - Create settlment invoice
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Plus menu is "Billing -> Start facturatierun"
        Then  Modal dialog is "Start invoicerun"

        When "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"
        Then Invoice run is scheduled

        Given Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type" polling 450 seconds
