@DWP
@REGRESSION
@UAT08ONLY
@ALL
Feature: NUAT-549 Send manual drop

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-549
    Scenario: Send manual drop
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Marktberichten"
        Then "1st" List element with value at column "EAN-code & Producttype" is checked

        When List View action is "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code & Producttype"

        And Changes are confirmed
        And Label "Module" is "INITIATE STOP ACCESS"
        And Label "Label" is "Drop/Request Budget Meter"
        And "Testing" turn on
        And "Market mock" turn on
        Then Changes are confirmed

        When Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible in table
        Then Marketbericht with EAN "parameter:EAN-code & Producttype" and module "INITIATE STOP ACCESS" is in status "Geaccepteerd"
