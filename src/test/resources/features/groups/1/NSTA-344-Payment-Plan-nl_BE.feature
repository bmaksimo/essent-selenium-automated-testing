@REGRESSION
@DWP
@B2C
@ALL
Feature: NSTA-344:Payment Plan

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-344
    Scenario: Payment plan for B2C
        #Create an active contract
        When Create active B2C contract with metering "On" and sign date "35 days before now"

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        #run invoice
        When Billing run "RECURRING" is triggered with process date "1 month from now"

        And Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" within 120 seconds
        And Table "Transacties" contains value "Issued" at column "Extra info" within 1800 seconds
        Then Click on link in View List at "1st" row and "ID & Type" column polling 60 seconds
        And Save Invoice Sum
        And Check Payment Plan with invoice "parameter:invoiceAmount"

        #Create a payment plan for this customer
        When Dashboard menu is "Billing"
        And List option is "ENKEL FACTUREN"
        And Invoice checkbox with key "InvoicesOnAccountOpenBalance" is clicked
        And List option is "AANVRAAG AFBETALINGSPLAN"

        And Input in "Type afbetalingsplan" is "Per bedrag"
        And Input in "Periode schijven" is "Maandelijks"
        And "Startdatum" date is "now"
        And "Bedrag eerste afbetalingsschijf" input is "parameter:firstInstallment"
        And "Bedrag andere afbetalingsschijven" input is "parameter:amountPerInstallment"
        Then Changes are confirmed waiting for 5 seconds

        #payment plan checks
        When Dashboard menu is "Billing"
        Then Click on link in View List at "1st" row and "Nummer & referentie" column polling 60 seconds
        And Save Installments Sum
        When Check is Number of Installments at least "2" for given amount "parameter:amountPerInstallment"
        Then Installments Amount of "parameter:installmentsAmount" is bigger than Invoice Amount of "parameter:invoiceAmount"
