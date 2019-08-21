@REGRESSION
@DWP
@B2C
@ALL
Feature: NSTA-344:Payment Plan

    Background:
        Given I login to iWelcome as "soapui_b2c"

    @NSTA-344
    Scenario: Payment plan for B2C
        #Create an active contract
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

        When Dashboard menu is "Contracten"
        And Get client number
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        #run invoice
        When Billing run "RECURRING" is triggered with process date "1 month from now"

        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" within 120 seconds
        And "1st" List element with value at column "ID & Type" is checked
        Then Click on link in View List at "1st" row and "ID & Type" column polling 60 seconds
        And Save Invoice Sum

        #Create a payment plan for this customer
        And List option is "ENKEL FACTUREN"
        And Table "Openstaande facturen" contains value "Invoice (ADVANCE)" at column "ID & Type" within 1200 seconds
        And Invoice checkbox with key "InvoicesOnAccountOpenBalance" is clicked
        And List option is "AANVRAAG AFBETALINGSPLAN"

        And Input in "Type afbetalingsplan" is "Per bedrag"
        And Input in "Periode schijven" is "Maandelijks"
        And "Bedrag eerste afbetalingsschijf" input is "50"
        And "Bedrag andere afbetalingsschijven" input is "50"
        And "Startdatum" date is "now"
        And Contract signature is confirmed

        #payment plan checks
        When Dashboard menu is "Billing"
        And Payment table is not empty
        And Table "Afbetalingsplannen" contains value "open" at column "Status" within 60 seconds
        Then Click on link in View List at "1st" row and "Nummer & referentie" column polling 60 seconds
        And Save Installments Sum
        Then Check is Number of Installments at least "2" for given amount "€ 50"
        Then Installments Amount of "parameter:installmentsAmount" is by "10" bigger than Invoice Amount of "parameter:invoiceAmount"
