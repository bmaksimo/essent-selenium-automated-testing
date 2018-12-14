@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-412

Feature: NUAT-412 part: Create / import coda file


#    Scenario: Create active contract TK1
#        Given  I logged in to DWP as salesmarketing.testautomation.b2c@essent.be
#        When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
#        And "Ondernemingsnummer" input is "BE0659881595"
#        And "Bedrijfsnaam" input is "Test Company B2B"
#        And Clicked on sign X
#        And New Quote is saved

#        When "Tariefdatum" date is "now"
#        And B2B sales channel is Inbound
#        And Quote details are confirmed

#        When Rechtsvorm is bvba
#        And First Name is Test and Last Name is "Test B2B"
#        And BEDRIJFSNAAM is Test Company B2B
#        And Telefoon is +3232331231
#        And Geslacht is Male
#        And E-mailadres is test@test.com
#        And Select Nace-Code
#        And NaceCode in search is 01120 - Teelt van rijst
#        And Customer Details are populated with: Address is Veldkant and HouseNumber is "2" and PostalCode is "2550" and City is "KONTICH"
#        And Customer details are confirmed
#        Then Form header is "Select package & fuel type"

#        When Package is "Vast"
#        And Checkbox "Gas Fix B2B (TC1)" is Unchecked
#        And Package and Fuel Type is confirmed
#        Then Form header is "Connection details"

#        When "Startdatum" date is "now"
#        And Ean-Code is 541448810000064421
#        And Connection details are confirmed
#        Then Form header is "Billing details"

#        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
#        And Billing details are confirmed
#        Then  Form header is "Quote overview"

#        When Option "Heeft de klant al getekend?" is On
#        And "Kanaal ondertekening" selection is "Papier"
#        And Quote is signed in Kontich
#        And "Datum ondertekening" date is "now"
#        And Quote is confirmed
#        Then View list header is "Offertes"
#        Then 1st list element has cell value Sales Getekend - Waarborg at column Type & status

#        When Dashboard menu is Details
#        Then Get Contract Number


#     #invoice run
#    @INVOICE-RUN
#    Scenario: Invoice run process
#        Given I renew login to DWP as billing.testautomation@essent.be
#        When Left menu is billing
#        And Top menu item is Klanten
#        And Top action is Filters
#        And "B2C/B2B" selection is "B2B"
#        And "Klantnummer" input is "parameter:contractNumber"

#        Then 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
#        When Plus menu is "Billing -> Start facturatierun"
#        And Modal dialog is Start invoicerun
#        And "Naam job" selection is "recurrent"
#        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
#        And "Factuurdatum" date is "now"
#        And "Procesdatum" date is "now"
#        Then Invoice run is scheduled
#        Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
#        When Dashboard menu is Billing
#        Then View list header is "Transacties"



      #create coda file
    @CREATE-CODA
    Scenario: Create CODA file in Odoo
        Given I logged in to Odoo as t.geets
        Given Cleanup Odoo CODA files
        When Odoo top menu is Accounting
        And  Odoo left menu is Customers
        And Odoo filter is 1000025737
        When Column "Account Number" with value "1000025737" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the "1st" row is clicked
        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Modal button "Close" is clicked

     #import coda file
    @IMPORT-CODA
    Scenario: Import CODA file in Odoo
        Given I renew login to Odoo as t.geets
        When Odoo top menu is Accounting
        When Odoo left menu is CODA Processing->Import CODA Files
        Then Odoo file upload dialog is Import CODA File
        Then CODA file is parameter:codaFile
        And  Odoo file upload confirm button is Import
        And  Odoo file import report
        And Modal button "View Bank Statement" is clicked
        When Column "Reference" of the "1st" row is clicked
        And Modal button "Close" is clicked
