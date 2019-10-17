@ALL
@DWP
@REGRESSION
@CREDIT-AND-CONTROL

Feature: NUAT-412: Import coda file
    Background:
        Given I login as API user "soapui_b2c"
    @NUAT-412
    Scenario: NUAT-412: Import coda file
        #1 - API contract creation
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

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #Create invoice via jBilling client call
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        And Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 10 times

        # 3 - Download CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:accountNumber"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Button "Outstanding" is clicked
        And Expand results
        And Generate CODA is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded

        # 4 - import and match CODA
        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "CODA Processing->Import CODA Files"
        Then Odoo file upload dialog is "Import CODA File"
        Then CODA file is "parameter:codaFile"
        And Odoo file upload confirm button is "Import"
        And Odoo file import report contains success string "Number of statements processed : 1"

        When Modal button "View CODA Bank Statement" is clicked