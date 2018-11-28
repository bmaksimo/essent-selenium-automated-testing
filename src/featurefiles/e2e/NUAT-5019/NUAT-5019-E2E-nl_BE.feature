#@5019-FULL-E2E
@E2E
@ODOO
Feature: NUAT-5019

    @5019-FULL-E2E
    Scenario: Run full end-to-end scenario
        #Create a B2C Quote with move in https://emagine-reality.atlassian.net/browse/NUAT-5019
        Given B2C TC1 Active Contract uses "FAKE" address and switch type is "MOVE IN"
        And I logged in to DWP as billing.testautomation@essent.be

        #invoice run advance
        When Left menu is billing
        And Top menu item is Klanten
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"

        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at 1st list row
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        Given Top arrow button is Up
        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is Start invoicerun
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        Then Invoice run is scheduled

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is Billing
        Then View list header is "Transacties"
        And 1st list element has cell value Invoice (ADVANCE) at column ID & Type



        #odoo import coda - NOT YET WORKING
        Given I renew login to Odoo as t.geets

#
#        When Odoo top menu is Accounting
#        And  Odoo left menu is CODA Processing->Import CODA Files
#        Then Odoo file upload dialog is Import CODA File
#        When CODA file is selected
#        And  Odoo file upload confirm button is Import
#        And  Odoo file import report


        Given I renew login to DWP as contracting.testautomation.b2c@essent.be

#generate consumptions
        When Top arrow button is Up
#        And Left menu is billing
#        And Top menu item is Klanten
#        And Top action is Filters
#        And "Klantnummer" input is "parameter:accountNumber"

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
        When Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        Given Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Verbruiken list is empty

        Given Top arrow button is Back
        When Consumption at deliverypointid parameter:EAN-code is generated from now until 2019-09-30
        And Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Consumption is available at 1st row in Van - Aan column

        #mediation run
        When Top arrow button is Up
#        And Left menu is billing
#        And Top menu item is Klanten
#        And Top action is Filters
#        And "Klantnummer" input is "parameter:accountNumber"

        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
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
#        When Top arrow button is Up
#        When Left menu is billing
#        And Top menu item is Klanten
#        And Top action is Filters

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
#        And 1st list element has cell value Invoice (SETTLEMENT) at column ID & Type


        #Reach HB3 dunning level and check if stop access (drop) has initiated
        When Top arrow button is Up
#        When Left menu is billing
#        And Top menu item is Klanten
#        And Top action is Filters
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column

        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds

        When Dashboard menu is Service
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
