@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-510 Triggering advance invoice run. Check is invoice created in DWP and jbilling

    Background:
        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Trigger Invoice run process
    
    		When Left menu is billing
        And Top menu item is Klanten
        #And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        #And "Klantnummer" input is "parameter:accountNumber"
        And "Klantnummer" input is "1000021383"
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