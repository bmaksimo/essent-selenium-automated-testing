@DWP
@B2B
@REGRESSION
@ALL

Feature: NUAT-417: Payment Plan creation/reversal

    @NUAT-417
    Scenario: Create active UP contract, run advance invoice, create and reverse payment plan
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        And B2B Active Contract is
            | productType | isFakeAddress | switchType      | meterType | kwMax |
            | TC1         | FAKE          | SUPPLIER SWITCH | YMR       | 50000 |

        #run invoice
        When Billing run "RECURRING" is triggered with process date "1 month from now"

        #check is invoice created
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Issued" at column "Extra info" within 1800 seconds

        #Payment plan creation
        And List option is "ENKEL FACTUREN"
        And Invoice checkbox with key "InvoicesOnAccountOpenBalance" is clicked
        And List option is "AANVRAAG AFBETALINGSPLAN"
        And Input in "Type afbetalingsplan" is "Per schijf"
        And Input in "Periode schijven" is "Maandelijks"
        And "Startdatum" date is "now"
        And "Aantal schijven" input is "5"
        Then Changes are confirmed waiting for 5 seconds

        #Payment plan check payment and status
        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Payment" at column "ID & Type" within 120 seconds

        #Reverse payment plan
        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And  Odoo left menu is Customers
        And Advanced search is
            |      field     |   operator  |          value          |
            | Account Number | is equal to | parameter:accountNumber |
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Journal Items" is clicked
        And Journal entry is open
        And Button "Reverse" on Journal Items is clicked
        And Button "Reverse" is clicked within Reverse modal

        #Check in DWP is payment plan reversed
        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Billing"
        And Table "Afbetalingsplannen" contains value "reversed" at column "Status" within 120 seconds
