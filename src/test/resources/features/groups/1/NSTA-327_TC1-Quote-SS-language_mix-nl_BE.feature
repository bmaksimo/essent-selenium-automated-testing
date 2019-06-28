@DWP
@SALES-MARKETING
@REGRESSION
@B2C
@API
@ALL
Feature: NSTA-327: Creating a B2C Quote TC1 with "Supplier Switch" without using Market Mock

    Background:
        Given I login to iWelcome as "soapui_b2c"

    @NSTA-327
    @SUPPLIER-SWITCH-NO-MM
    Scenario: Create a B2C Account with Quote, With supplier switch, without using Market Mock

        #Step 1. Selecteer type Offerte / Select Quote type
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
        And Contracted EAN exists on account

        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds

        #Step 9. Check market messages
        When Dashboard menu is "Marktberichten"
        Then View list header is "Marktberichten"
        Then Table "Marktberichten" contains value "START ACCESS Supplier Switch" at column "Module & Label"
        Then Table "Marktberichten" contains value "parameter:contractDate" at column "Status & ED"
        Then Table "Marktberichten" contains value "Gesloten" at column "Status & ED"

