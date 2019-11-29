@DWP
@REGRESSION
@B2C
@API
@ALL

Feature: TESTAUTO-395: Check if confirmation email has been sent

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-395
    Scenario: Check if confirmation email has been sent

        When "Create_Quote" flow is started
        And Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now" with communication by email
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
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 120 seconds

        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 600 seconds
        
        When Dashboard menu is "Service"
        Then Interaction is created with Type Document and Onderwerp "Outbound document: CONF_CONTRACT_SALES_TC1_B2C"

        When Click on link in View List at "2nd" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        Then EMC ID is present



