@ALL
@DWP
@E2E
@Ignore
Feature: NUAT-5019: Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I login as API user "soapui_b2c"
    @NUAT-5019
    Scenario: Create active contract that after dunning the contract becomes inactive

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

        # 2 - invoice run advance
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is Customers
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Outstanding" is clicked
        And Expand results
        And Generate CODA is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded

        # 4 - import and match CODA
        When Modal button "Close" is clicked
        When Odoo top menu is "Accounting"
        And Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report contains success string "Number of statements processed : 1"

#        Given I renew login to DWP as "billing.testautomation@essent.be"
        Given I renew login to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is Filter from "contracting-switching" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"

        When Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Payment" at column "ID & Type"
        And Table "Transacties" contains value "0" at column "Openstaand bedrag"
        And Table "Transacties" contains value "Paid by OV" at column "Extra info"

        # 5 - Create consumptions
        Then Consumption at deliverypointid "parameter:EAN-code" is generated from "35 days before now" until "12" months after

        # 6 - Run mediation
        When Mediation run "Advance" is triggered with settlement date "11 months from now"

        # 7 - Create settlement invoice
        When Billing run "ONETIME" is triggered with process date "11 months from now"

        # 8 - Reach HB3 dunning level
        And Dunning day countdown for "parameter:accountNumber" goes down 12 days
        And Sleep for 60 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds
        And Dunning day countdown for "parameter:accountNumber" goes down 28 days
        And Sleep for 90 seconds

        When Dashboard menu is "Contracten"
        When Dashboard menu is "Billing"
        And Table "Transacties" contains value "Invoice (SETTLEMENT)" at column "ID & Type"
        And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type"
        And Table "Transacties" contains value "Payment" at column "ID & Type"
        And "1st" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "2nd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"
        And "3rd" list element has cell value "Invoice (DUNNINGCOST)" at column "ID & Type"

        # 9 - Soft dunning
        And Dashboard menu is "Service"

        And Table "Taken" contains value "Soft-Dunning Call POST HB3 B2C HIGH" at column "Naam & Type & Subtype"
        And Table "Taken" contains value "Soft-Dunning Call POST HB2 B2C HIGH" at column "Naam & Type & Subtype"
        And Table "Taken" contains value "Soft-Dunning Call POST HB1 B2C HIGH" at column "Naam & Type & Subtype"

        When Dashboard menu is "Marktberichten"
        Then Table "Marktberichten" contains value "INITIATE STOP ACCESS" at column "Module & Label" retrying 5 times
        # 11 - Cancel INITIATE STOP ACCESS market message and create a new INITIATE STOP ACCESS market message effective from NOW
        When Plus action of "1" element from "MarketTransactionsOnAccount" and click on "Annuleer Marktbericht"
        And Select Contractline dialog is confirmed

        When Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code"
        Then Select Contractline dialog is confirmed
        When "Module" selection is "INITIATE STOP ACCESS"
        And "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" date is "1 day before now"
        And Option "Testing?" "is" "On"
        And Select Contractline dialog is confirmed
        Then "1st" list element has cell value "INITIATE STOP ACCESS" at column "Module & Label" polling 450 seconds
        And Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible in table

        When Dashboard menu is "Contracten"
        And "Actieve en toekomstige connecties" list is empty
        And Table "Contracten" contains value "Inactief" at column "Type & status"
