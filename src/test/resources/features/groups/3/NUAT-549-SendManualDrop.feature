@DWP
@REGRESSION
@UAT08ONLY
@ALL
Feature: NUAT-549 Send manual drop

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-549
    Scenario: Send manual drop
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Marktberichten"
        And "1st" List element with value at column "EAN-code & Producttype" is checked

        When List View action is "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code & Producttype"
        And Changes are confirmed
        And Label "Module" is "INITIATE STOP ACCESS"
        And Label "Label" is "Drop/Request Budget Meter"
        And "Testing" turn on
        And "Market mock" turn on
        And Changes are confirmed

        Then "1st" list element has cell value "Geaccepteerd" at column "Status & ED" polling 550 seconds
