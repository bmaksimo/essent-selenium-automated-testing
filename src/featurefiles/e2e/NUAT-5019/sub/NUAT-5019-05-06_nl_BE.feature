@DWP
Feature: NUAT-5019 Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I renew login to DWP as "billing.testautomation@essent.be"
    @NUAT-5019-05-07
    Scenario: Create active contract that after dunning the contract becomes inactive


        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        #And "Naam" input is "parameter:suitecrm-customer-name"
        #And "Klantnummer" input is "parameter:accountNumber"
        And "Klantnummer" input is "1000099853"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        #When Click on "parameter:suitecrm-customer-name" link
        When Click on "1000099853" link
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "0"


        # 5 - Create consumptions
        Given Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated from now until "2019-09-30"
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
        #And "Klantnummer" input is "parameter:accountNumber"
        And "Klantnummer" input is "1000099853"
        #Then  Click on "parameter:suitecrm-customer-name" link
        And Click on "1000099853" link
        And Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (SETTLEMENT)" at column "ID & Type"
