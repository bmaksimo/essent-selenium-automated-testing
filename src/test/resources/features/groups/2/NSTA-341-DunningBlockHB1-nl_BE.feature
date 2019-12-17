@Ignore
Feature: NSTA-341: Block dunning for invoice

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-341
    Scenario: Create active contract that after dunning the contract becomes inactive
        #1 - API contract creation
        And Create active B2C contract with metering "On" and sign date "35 days before now"

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #Create invoice via jBilling client call
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 10 times

        # 3 - Block dunning for the invoice
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Plaats aanmaningsblokkade op factuur"
        And "Reden voor blokkade" selection is "Klacht"
        And Changes are confirmed
        Then Table "Transacties" contains check mark at column "Geblokkeerd?"

        # 4 - Trigger dunning
        Given Dunning day countdown for "parameter:accountNumber" goes down 12 days
        Then Table "Transacties" does not contain value "Invoice (DUNNINGCOST)" at column "ID & Type" within 60 seconds
