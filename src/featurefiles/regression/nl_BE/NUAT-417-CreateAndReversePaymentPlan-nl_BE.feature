@DWP
@B2B
@REGRESSION
@NUAT-417

Feature: NUAT-417: Create a Payment Plan for active contract with meterType YMR, run advance invoice, create payment plan


       Scenario: Create active UP contract, run advance invoice
            Given I logged in to DWP as billing.testautomation@essent.be
            When Left menu is billing
            And Top menu item is Klanten
            Given B2B Active Contract is
                | productType    | isFakeAddress | switchType       | meterType | kwMax |
                | UP             | FAKE          |  SUPPLIER SWITCH | YMR       | 50000 |
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "parameter:accountNumber"
            Then 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

            #run invoice
            When Plus menu is "Billing -> Start facturatierun"
            And Modal dialog is Start invoicerun
            And "Naam job" selection is "recurrent"
            And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
            And "Factuurdatum" date is "now"
            And "Procesdatum" date is "now"
            Then Invoice run is scheduled

            Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
            When Dashboard menu is Billing
            Then View list header is "Transacties"
            And 1st list element has cell value Invoice (ADVANCE) at column ID & Type
            And 1st List element with value at column "ID & Type" is checked


        @payment-plan
       Scenario: Payment plan creation
            Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "parameter:accountNumber"
            Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
            When Dashboard menu is Billing
            Then View list header is "Transacties"
            And 2nd list element has cell value Invoice (ADVANCE) at column ID & Type
            And List option is "ENKEL FACTUREN"
            And View list header is "Openstaande facturen"
            And Invoice with key InvoicesOnAccountOpenBalance is checked
            And List option is "AANVRAAG AFBETALINGSPLAN"


            And Input in Type afbetalingsplan is "Bedrag"
            And Input in Periode schijven is "Maandelijks"
            And "Startdatum" date is "now"
            And "Aantal schijven" input is "5"
            And Contract signature is confirmed

            When Dashboard menu is Billing
            Then View list header is "Afbetalingsplannen"
            And Table Afbetalingsplannen contains value "open" at column Status



        #payment plan verification
        Scenario: Check is payment plan created when logged as billing user
            Given I logged in to DWP as billing.testautomation@essent.be
            When Left menu is billing
            And Top menu item is Klanten
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "parameter:accountNumber"
            Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
            When Dashboard menu is Billing
            Then View list header is "Transacties"
            And 1st list element has cell value Payment at column ID & Type

        #Reverse Payment Plan
        Scenario: Reverse payment plan
            Given I logged in to Odoo as t.geets
            When Odoo top menu is Accounting
            And  Odoo left menu is Customers
            And Odoo filter is parameter:accountNumber
            When Column "Account Number" with value "parameter:accountNumber" is clicked
            And Button "Journal Items" is clicked
            And Journal entry is open
            And Modal buttons "Reverse" are clicked


            #check in dwp is payment plan status reversed
        Scenario: Check is payment plan reversed
            Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And Top action is Filters
            And "B2C/B2B" selection is "B2B"
            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "parameter:accountNumber"
            Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
            When Dashboard menu is Billing
            Then View list header is "Afbetalingsplannen"
            And Table Afbetalingsplannen contains value "reversed" at column Status










