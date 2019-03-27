@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@UNSTABLE
Feature: NUAT-557: Check guarantee status of customer with Customer Acceptance Tool
    Background:
        When B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
    @NUAT-557
    Scenario: Check status of customer with Customer Acceptance Tool
        Given  I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And  Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2B"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Details"
        Then Get Company Number

        When Top arrow button is "UP"
        And Plus menu is "Sales -> TK1 -> Klantacceptatie tool"
        And "Ondernemingsnummer" input is "parameter:companyNumber"
        Then "Klantacceptatie" field value is "Waarborg"
        #Then Customer Status is "Waarborg"

