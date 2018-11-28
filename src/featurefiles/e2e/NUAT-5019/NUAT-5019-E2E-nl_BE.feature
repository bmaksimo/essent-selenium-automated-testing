@E2E
@ODOO
Feature: NUAT-5019

    @5019-FULL-E2E
    Scenario: Run full end-to-end scenario
        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

        #GUI contract creation
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is Inbound
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

        When "Startdatum" date is "now"
        And Electricity EAN code is "random"
        And Switch type is Move in
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"


        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then 1st list element has cell value Sales Getekend - Geaccepteerd at column Type & status

        When Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And  1st List element with value at column "EAN-code" is checked
        And  1st list element has cell value Actief at column "Contractnummer" polling 450 seconds

        Given I renew login to DWP as billing.testautomation@essent.be

        #invoice run advance
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at 1st list row
        And View List element "Klantnummer & Naam" using "accountNumber" as alias is collected as parameter at 1st list row
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is Up
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is Start invoicerun@5019-FULL-E2E
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        Then Invoice run is scheduled

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (ADVANCE) at column ID & Type

        #odoo import coda
        Given I renew login to Odoo as t.geets
        When Odoo top menu is Accounting
        And Odoo left menu is CODA Processing->Import CODA Files
        Then Odoo file upload dialog is Import CODA File
        When CODA file is selected
        And Odoo file upload confirm button is Import
        Then Odoo file import report

        Given I renew login to DWP as billing.testautomation@essent.be

        #check if payment is reconciled
        Given Left menu is contracting-switching
        And Top menu item is Klanten
        And Top action is Filters
        When "Klantnummer" input is "parameter:accountNumber"
        Then View list header is "Klanten"

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
        When Dashboard menu is Billing
        Then Transacties list is not empty
        And "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"


        #generate consumptions
        When Top arrow button is Up

        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        When Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Verbruiken list is empty

        Given Top arrow button is Back
        When Consumption at deliverypointid parameter:EAN-code is generated from now until 2019-09-30
        And Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Consumption is available at 1st row in Van - Aan column

        #mediation run
        Given Top arrow button is Up
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        When Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is Up
        When Plus menu is "Billing -> Start mediationrun"
        Then Modal dialog is Start mediationrun
        Given "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Datum afrekeningsfactuur" date is "parameter:billrun-date"
        Then Form is submitted

        #Trigger Invoice run process
        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
        When  Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is Up
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is Start invoicerun
        And "Naam job" selection is "Eenmalig"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "parameter:billrun-date"

        Then Invoice run is scheduled

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        # invoice is still not being created - CHECKING NEEDED
        And 1st list element has cell value Invoice (SETTLEMENT) at column ID & Type


        #Reach HB3 dunning level and check if stop access (drop) has initiated
        When Top arrow button is Up
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column

        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds

        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 2nd list element has cell value Invoice (DUNNINGCOST) at column ID & Type
        And 3rd list element has cell value Invoice (DUNNINGCOST) at column ID & Type

        # COLLECT LETTERS ARE NOT BEING CREATED IN UAT08 
        When Dashboard menu is Service

#        Then Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 1st row
#        And Table Interacties contains cell value Outbound document: Dunning SMS HB3 at column Type & Onderwerp on 2nd row
#        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 3rd row
#        And Table Interacties contains cell value Outbound document: CollectionLetter at column Type & Onderwerp on 4th row

        #Billing - Soft dunning process tasks check
        Then Table Taken contains value "Soft-Dunning Call POST HB3 B2C HIGH" at column Naam & Type & Subtype
        And  Table Taken contains value "Soft-Dunning Call POST HB2 B2C HIGH" at column Naam & Type & Subtype
        And  Table Taken contains value "Soft-Dunning Call POST HB1 B2C HIGH" at column Naam & Type & Subtype

        #Navigate to GUI checks
        When Dashboard menu is Marktberichten
        Then 1st list element has cell value INITIATE STOP ACCESS at column Module & Label

        #Send email to SME with test results
        Then Send email to SMEs
