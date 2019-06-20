@DWP
@B2B
@REGRESSION
@ALL

Feature: NUAT-417: Payment Plan creation/reversal

    @NUAT-417
    Scenario: Create active UP contract, run advance invoice
        Given I logged in to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        Given B2B Active Contract is
            | productType | isFakeAddress | switchType      | meterType | kwMax |
            | UP          | FAKE          | SUPPLIER SWITCH | YMR       | 50000 |
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        And Sleep for 30 seconds
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        #run invoice
        When Plus menu is "Billing -> Start facturatierun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        And Modal dialog is "Start invoicerun"
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
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds

        #Payment plan creation
        Given I renew login to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Transacties"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds
        And List option is "ENKEL FACTUREN"
        And View list header is "Openstaande facturen"
        And Invoice checkbox with key "InvoicesOnAccountOpenBalance" is clicked
        And List option is "AANVRAAG AFBETALINGSPLAN"
        And Input in "Type afbetalingsplan" is "Per schijf"
        And Input in "Periode schijven" is "Maandelijks"
        And "Startdatum" date is "now"
        And "Aantal schijven" input is "5"
        Then Contract signature is confirmed

        Given I renew login to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds

        When Dashboard menu is "Billing"
        Then View list header is "Afbetalingsplannen"
        And Table "Afbetalingsplannen" contains value "open" at column "Status"

        #Check is payment plan created when logged as billing user
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Payment" at column "ID & Type"

        #Reverse payment plan
        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And  Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked

        And Button "Journal Items" is clicked
        And Sleep for 30 seconds
        And Journal entry is open
        And Sleep for 30 seconds
        And Modal button "Reverse" clicked

        #Check in DWP is payment plan reversed
        Given I renew login to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        Then View list header is "Afbetalingsplannen"
        And Table "Afbetalingsplannen" contains value "reversed" at column "Status"
