@DWP
@E2E
Feature: NUAT-5019 Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @NUAT-5019
    Scenario: Create active contract that after dunning the contract becomes inactive
        # 1 - GUI contract creation
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

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "2 weeks before now"
        And Electricity EAN code is "random"
        And Electricity market mock test is Open
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
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        # 2 - invoice run advance
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at "1st" list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at "1st" list row
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column
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
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type"

        # 3 - import and match CODA
        Given I renew login to Odoo as "t.geets"
        And Cleanup Odoo CODA files
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the "1st" row is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Modal button "Close" is clicked

        When Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report
        And Modal button "View Bank Statement" is clicked
        And Wait for 30 seconds
        When Column "Reference" of the "1st" row is clicked
        And Bank Statement "Close" button is clicked

        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"

        And Click on link in View List at "1st" row and "Klantnummer & Naam" column
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"

        # 4 - Create consumptions
        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked

        Given Click on link in "Actieve en toekomstige connecties" View List at "1st" row and "EAN-code" column
        Then View list header is "Verbruiken"
        And "Verbruiken" list is empty

        Given Top arrow button is "Back"
        When Consumption at deliverypointid "parameter:EAN-code" is generated from now until "2019-09-30"
        And Click on link in "Actieve en toekomstige connecties" View List at "1st" row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Consumption is available at "1st" row in "Van - Aan" column

        # 5 - Run mediation
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked


        Given Top arrow button is "Up"
        When Plus menu is "Billing -> Start mediationrun"
        Then Modal dialog is "Start mediationrun"
        Given "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Datum afrekeningsfactuur" date is "parameter:billrun-date"
        Then Form is submitted

        # 6 - Create settlment invoice
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked

        Given Top arrow button is "Up"
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"

        Then Invoice run is scheduled

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type"

        # 8 - Reach HB3 dunning level
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then View list header is "Klanten"
        And View List element "Id Billing customer & persoon/familie sleutel" using "billingCustomerId" as alias is collected as parameter at "1st" list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at "1st" list row

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds

        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "2nd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "3rd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "4th" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type"
        And "5th" list element has cell value "Invoice (ADVANCE)" at column "ID & Type"
        And "6th" list element has cell value "Payment" at column "ID & Type"

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
        When Click on link in "Marktberichten" View List at "1st" row and "Plus Action" column
        And Row actions "Annuleer Marktbericht" is clicked
        And Select Contractline dialog is confirmed

        When Click on "Start nieuw marktbericht"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code"
        Then Select Contractline dialog is confirmed
        When "Module" selection is "INITIATE STOP ACCESS"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" date is "now"
        And Option "Testing?" is On
        And Select Contractline dialog is confirmed
        Then "1st" list element has cell value "INITIATE STOP ACCESS" at column "Module & Label" polling 450 seconds
        Then Wait for 180 seconds

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "Actieve en toekomstige connecties" list is empty
        And Table "Contracten" contains value "Inactief" at column "Type & status"
        And Send email to SMEs
