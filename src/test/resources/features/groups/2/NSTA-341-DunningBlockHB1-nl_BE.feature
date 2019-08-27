@ALL
@DWP
@REGRESSION
@API
@B2C
Feature: NSTA-341: Block dunning for invoice

    Background:
        Given I login to iWelcome as "soapui_b2c"

    @NSTA-341
    Scenario: Create active contract that after dunning the contract becomes inactive
        #1 - API contract creation
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "On" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"
        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"
        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #Create invoice via jBilling client call
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" within 120 seconds

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
