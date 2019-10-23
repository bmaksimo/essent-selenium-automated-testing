@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-557: Check valid statuses of B2B customer with Customer Acceptance Tool
    Background:
        Given B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
    @NUAT-557
    Scenario: NUAT-557: Check status of customer with Customer Acceptance Tool
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        And Click on top menu button PLUS and navigate to "Sales -> TK1 -> Klantacceptatie tool"
        When "Ondernemingsnummer" input is "parameter:companyNumber"
        Then Customer Status is an existing status
