@DWP
@REGRESSION
@B2C
@API
@ALL

Feature: TESTAUTO-381: Create B2C contract with customer from Wallonia

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-381
    Scenario: Create B2C contract with customer from Wallonia

        When "Create_Quote" flow is started
        And Data is prepared for Customer "Wallonia" and "prospect" and sign date is "35 days before now"
        And New tc1_quote is created
        And Quote status is "ACCEPTED"
        And Quoteline exists
        Then Quoteline status is "Sent to customer"

        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"

        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        And Contracted EAN exists on account

        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Billing run "RECURRING" is triggered with process date "1 month from now"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        #Check contract activation and invoice creation
        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 600 seconds
        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 5 times


