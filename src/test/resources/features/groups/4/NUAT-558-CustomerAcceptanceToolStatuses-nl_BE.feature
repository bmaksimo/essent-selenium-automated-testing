@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-558: Check status of customer with Customer Acceptance Tool

    Background:
    	Given B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    @NUAT-558
    Scenario: NUAT-558: Check status of customer with Customer Acceptance Tool
        And I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        And Plus menu is "Sales -> UP/TK2 -> Uitzonderingslijst klantacceptatie"
        And Click on "UTZONDERING KLANTACCEPTATIE TOEVOEGEN"
        And Modal "Customer acceptance exception" is displayed
        And "Ondernemingsnummer" input is "parameter:companyNumber"
        And "Status" selection is "Deny"
        And "Geldig tot" date is "31 days from now" and time is "now"

        When Changes are confirmed
        And Plus menu is "Sales -> UP/TK2 -> Klantacceptatie tool"
        And "Ondernemingsnummer" input is "parameter:companyNumber"

        Then Customer Status is "Geweigerd"
