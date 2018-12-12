@DWP
@B2B
@REGRESSION
@JBILLING
Feature: NUAT-510 Triggering advance invoice run. Check is invoice created in DWP and jbilling

    #Background:
        #Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Trigger Invoice run process

        When Left menu is billing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        #And "Klantnummer" input is "1000021383"
        Then 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

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

				
				# JBilling
        Given I logged in to JBilling as billing_testautomation
        When JBilling top menu item is "Customers"
        And JBilling "Login Name" input is "parameter:Id Billing customer & persoon/familie sleutel"
        #And JBilling "Login Name" input is "1000955289"
        And JBilling Click on "Apply Filters"
        And JBilling Click on row "1" in Table
        And JBilling Click on text link "Show all invoices"
        Then Invoices table is not empty
        And JBilling First cell value in first row is "parameter:ID & Type"
        #And JBilling First cell value in first row is "VKW6820000393"
        When JBilling Click on row "1" in Table
        Then JBilling label "Invoice Number" contains value "parameter:ID & Type" at column "2"
        #Then JBilling label "Invoice Number" contains value "VKW6820000393" at column "2"
        And Inner tables are not empty
        
        # Check orders in jbilling
        When JBilling top menu item is "Orders"
				And JBilling "Login Name" input is "parameter:Id Billing customer & persoon/familie sleutel"
				#And JBilling "Login Name" input is "1000955289"
				And JBilling Click on "Apply Filters"
				Then Order table is not empty
				When JBilling Click on row "1" in Table
				Then JBilling label "User Name:" contains value "parameter:Id Billing customer & persoon/familie sleutel" at column "2"
				#Then JBilling label "User Name:" contains value "1000955289" at column "2"
				And Inner tables are not empty