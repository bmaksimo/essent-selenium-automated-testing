@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-558: Check status of customer with Customer Acceptance Tool

    Background:
    	Given B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    @NUAT-558
    Scenario: Check status of customer with Customer Acceptance Tool
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column waiting for 60 seconds
        And Dashboard menu is "Details"
        Then Get Company Number

        When Top arrow button is "Up"
        And Plus menu is "Sales -> UP/TK2 -> Uitzonderingslijst klantacceptatie"
        And Click on "UTZONDERING KLANTACCEPTATIE TOEVOEGEN"
        Then Modal "Customer acceptance exception" is displayed

        Given "Ondernemingsnummer" input is "parameter:companyNumber"
        And "Status" selection is "Deny"
        And "Geldig tot" date is "31 days from now" and time is "now"
        When Changes are confirmed
        And Plus menu is "Sales -> UP/TK2 -> Klantacceptatie tool"
        And "Ondernemingsnummer" input is "parameter:companyNumber"
        Then Customer Status is "Geweigerd"
