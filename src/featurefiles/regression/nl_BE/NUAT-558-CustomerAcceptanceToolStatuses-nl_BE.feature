@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-558

Feature: NUAT-558: Check status of customer with Customer Acceptance Tool

        Scenario: Check status of customer with Customer Acceptance Tool
        Given  I logged in to DWP as salesmarketing.testautomation.b2c@essent.be
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Details
        Then Get Company Number

        When Top arrow button is UP
        When Plus menu is "Sales -> TK1 -> Klantacceptatie tool"
        And "Ondernemingsnummer" input is "parameter:companyNumber"
        Then Customer Status is "Geaccepteerd"
