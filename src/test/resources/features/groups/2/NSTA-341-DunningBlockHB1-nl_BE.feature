@ALL
@DWP
@REGRESSION
@B2C
Feature: NSTA-341: Block dunning for invoice

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @NSTA-341
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
        And "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        And "1st" List element with value at column "EAN-code" is checked
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        And Get Account Number
        And Dashboard menu is "Details"
        And Get billing number

        Given Top arrow button is "Up"
        And Plus menu is "Billing -> Start facturatierun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        When Modal dialog is "Start invoicerun"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:billingNumber"
        Then Invoice run is scheduled

        And Sleep for 20 seconds
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And "Klantnummer" input is "parameter:accountNumber"

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds

        # 3 - Block dunning for the invoice
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Plaats aanmaningsblokkade op factuur"
        And "Reden voor blokkade" selection is "Klacht"
        And Changes are confirmed
        Then Table "Transacties" contains check mark at column "Geblokkeerd?"

        # 4 - Trigger dunning
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        When Dashboard menu is "Contracten"
        When Dashboard menu is "Billing"
        And Table "Transacties" does not contain value "Invoice (DUNNINGCOST)" at column "ID & Type"
