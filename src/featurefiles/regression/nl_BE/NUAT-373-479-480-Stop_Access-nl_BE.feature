@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-373-479-480
@UAT08ONLY
Feature: NUAT-373: End Of Contract For Bankruptcy - nl_BE, NUAT-479: DROP For A Non Residential Client, NUAT-480: End Of Contract Via DWP - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Marktberichten"
        Then "1st" List element with value at column "EAN-code & Producttype" is checked

        When List View action is "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Contract line EAN-code "parameter:EAN-code & Producttype" is submitted
        Then Changes are confirmed
        And Modal dialog is "Start new market scenario"

    @NUAT-373
    @END-CONTRACT-FOR-BANKRUPTCY
    Scenario: NUAT-373 End of contract for bankruptcy
        When "Module" selection is "INITIATE STOP ACCESS"
        And  "Label" selection is "Non-Residential End-of-Contract"
        And Option "Testing?" is On
        And Option "Market mock?" is On
        And "Effective Date" date is "1 month from now"
        And Changes are confirmed
        Then Confirm task was "Non-Residential End-of-Contract"

    @NUAT-479
    @NON-RESIDENTIAL-DROP
    Scenario: NUAT-479 DROP for a Non-residential client
        When "Module" selection is "INITIATE STOP ACCESS"
        And  "Label" selection is "Drop/Request Budget Meter"
        And Option "Testing?" is On
        And Option "Market mock?" is On
        And Changes are confirmed
        Then  Confirm task was "Non-Residential Drop"

    @NUAT-480
    @END-CONTRACT-DWP
    Scenario: NUAT-480 End of contract via DWP
        When "Module" selection is "INITIATE STOP ACCESS"
        And  "Label" selection is "Non-Residential End-of-Contract"
        And "Effective Date" date is "1 month from now"
        And Option "Testing?" is On
        And Changes are confirmed
        Then Confirm task was "Non-Residential End-of-Contract"
        When Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible
        Then Confirm status is "Geaccepteerd"
