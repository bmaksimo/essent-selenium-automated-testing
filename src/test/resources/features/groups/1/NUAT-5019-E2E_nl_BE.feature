@ALL
@DWP
@E2E
@REGRESSION
@UAT08ONLY
Feature: NUAT-5019: Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @NUAT-5019
    Scenario: Create active contract that after dunning the contract becomes inactive
        #1 - GUI contract creation
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
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        And EAN code is generated
        And "Startdatum" date is "35 days before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And  Option "test" "is" "On"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
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

        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        When Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Payment" at column "ID & Type"
        And Table "Transacties" contains value "0" at column "Openstaand bedrag"
        And Table "Transacties" contains value "Paid by OV" at column "Extra info"

        # 5 - Create consumptions
        Given Dashboard menu is "Contracten"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated from "35 days before now" until "12" months after

        # 6 - Run mediation
        When Top arrow button is "Up"
        And Left menu is "billing"
        And Top menu item is "Klanten"
        And Plus menu is "Billing -> Start mediationrun"
        Then Modal dialog is "Start mediationrun"

        Given "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Datum afrekeningsfactuur" date is "11 months from now"
        Then Form is submitted

        # 7 - Create settlement invoice
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Plus menu is "Billing -> Start facturatierun"
        Then  Modal dialog is "Start invoicerun"

        When "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "11 months from now"
        Then Invoice run is scheduled

        Given Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (SETTLEMENT)" at column "ID & Type"

        # 8 - Reach HB3 dunning level
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at "1st" list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at "1st" list row

        Given Click on "parameter:accountNumber" link
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds

        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (SETTLEMENT)" at column "ID & Type"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type"
        And Table "Transacties" contains value "Payment" at column "ID & Type"
        And "1st" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "2nd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "3rd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"

        # 9 - Soft dunning
        And Dashboard menu is "Service"

        And Table "Taken" contains value "Soft-Dunning Call POST HB3 B2C HIGH" at column "Naam & Type & Subtype"
        And Table "Taken" contains value "Soft-Dunning Call POST HB2 B2C HIGH" at column "Naam & Type & Subtype"
        And Table "Taken" contains value "Soft-Dunning Call POST HB1 B2C HIGH" at column "Naam & Type & Subtype"

        # 10 - Check for INITIATE STOP ACCESS market message creation
        Given I renew login to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Dashboard menu is "Marktberichten"
        Then "1st" list element has cell value "INITIATE STOP ACCESS" at column "Module & Label" polling 450 seconds

        # 11 - Cancel INITIATE STOP ACCESS market message and create a new INITIATE STOP ACCESS market message effective from NOW
        When Plus action of "1" element from "MarketTransactionsOnAccount" and click on "Annuleer Marktbericht"
        And Select Contractline dialog is confirmed

        When Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code"
        Then Select Contractline dialog is confirmed
        When "Module" selection is "INITIATE STOP ACCESS"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" date is "1 day before now"
        And Option "Testing?" "is" "On"
        And Select Contractline dialog is confirmed
        Then "1st" list element has cell value "INITIATE STOP ACCESS" at column "Module & Label" polling 450 seconds
        And Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible in table

        When Dashboard menu is "Contracten"
        And "Actieve en toekomstige connecties" list is empty
        And Table "Contracten" contains value "Inactief" at column "Type & status"
