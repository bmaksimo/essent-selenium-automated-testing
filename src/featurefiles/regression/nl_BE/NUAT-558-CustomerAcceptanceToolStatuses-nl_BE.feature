@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-558

Feature: NUAT-558: Check status of customer with Customer Acceptance Tool

    Background:
        Scenario: Check status of customer with Customer Acceptance Tool
        Given  I logged in to DWP as salesmarketing.testautomation.b2c@essent.be
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

        When Plus menu is "Sales -> TK1 -> Klantacceptatie tool"
        And "Ondernemingsnummer" input is "parameter:accountNumber"
        Then Customer Status is "Geaccepteerd"


