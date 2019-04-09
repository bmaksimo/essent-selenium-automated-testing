@REGRESSION
@DWP
@B2C

Feature: NSTA - 344 Payment Plan

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-344
    Scenario: Payment plan for B2C
        #Create an active contract
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

        When "Startdatum" date is "35 days before now"
        And Electricity EAN code is "random"
        And "Type aansluiting" selection is "YMR"
        And "Meternummer" input is "1000"
        And Option "test" is On
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Quote is signed
        And Quote is signed in "Kontich"
        When Quote is confirmed

        When Dashboard menu is "Marktberichten"
        Then View List is empty

        When Dashboard menu is "Contracten"
        And Get client number
        Then View list header is "Actieve en toekomstige connecties"
        And  "1st" List element with value at column "EAN-code" is checked
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        #run invoice
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
        When Plus menu is "Billing -> Start facturatierun"
        And Modal dialog is "Start invoicerun"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        Then Invoice run is scheduled

        Given I renew login to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type"
        And "1st" List element with value at column "ID & Type" is checked

        Create a payment plan for this customer
        And List option is "ENKEL FACTUREN"
        And View list header is "Openstaande facturen"
        And Invoice checkbox with key "InvoicesOnAccountOpenBalance" is clicked
        And List option is "AANVRAAG AFBETALINGSPLAN"

        And Input in "Type afbetalingsplan" is "Per schijf"
        And Input in "Periode schijven" is "Maandelijks"
        And "Bedrag eerste afbetalingsschijf" input is "50"
        And "Bedrag andere afbetalingsschijven" input is "50"
        And "Startdatum" date is "now"
        And Contract signature is confirmed

        #payment plan checks
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        Then View list header is "Afbetalingsplannen"
        And Payment table is not empty
        And Table "Afbetalingsplannen" contains value "open" at column "Status"
        Then Payment plan has "3" installments
        Then Payment plan has installment values of "€ 50", "€ 50" and "€ 13"




