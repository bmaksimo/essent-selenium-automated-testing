@ALL
@DWP
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-412 part: Create / import coda file
    Background:
        Given  I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @NUAT-412
    Scenario: Create active contract via GUI and Create / import coda file
        # 1 - GUI B2C contract creation
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        And "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        And Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        And EAN code is generated
        And "Startdatum" date is "35 days before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And  Option "test" "is" "On"
        And Connection details are confirmed
        And "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        And Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        And "1st" List element with value at column "EAN-code" is checked
        And Get Account Number
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
        Given Top arrow button is "Up"

        # 2 - invoice run advance
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Billing"
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

        When Modal button "View Bank Statement" is clicked
        And Column "Reference" of the "1st" row is clicked
