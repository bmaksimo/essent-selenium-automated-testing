@REGRESSION
@DWP
@B2C
@API
@ALL
Feature: NSTA-345:Credit Invoice

    Background:
        Given I login to iWelcome as "soapui_b2c"

    @NSTA-345
    Scenario: Credit Invoice
        #Create an active contract via API
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect"
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

        #2 - invoice run advance
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at "1st" list row
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
        And Top arrow button is "UP"

        And Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "1 month from now"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        Then Invoice run is scheduled

        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"

        #Recalculate invoice
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Herbereken tussentijdse factuur"
        And New Amount Invoice is "300" for EAN "parameter:EAN-code"
        Then Invoice run is scheduled

        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Service"
        Then View list header is "Interacties"

          #3 - Check if interactions are created for VKM and CNM
        Then Table "Interacties" contains value "VKM" at column "Type & Onderwerp"
        Then Table "Interacties" contains value "CNM" at column "Type & Onderwerp"
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"

        #Asserts
         #1 - Check if Old invoice is credited (check if CNM is created for same amount as old VKM)
         #2 - Check if there is a new invoice created for the amount you selected
        Then Invoice Amounts have values "600 €", "-600 €" and "1500 €"

         #4 - Check if CNM has been sent to customer
        When Dashboard menu is "Service"
        Then View list header is "Interacties"
        And Click on link in View List at "2nd" row and "Nummer & Communicatiekanaal" column waiting for 40 seconds
        Then Sleep for 20 seconds
        Then Check is product change "1 succeeded"

         #5 - Check Balance of new invoice credit
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        Then Balance is "€ 1500,00"









