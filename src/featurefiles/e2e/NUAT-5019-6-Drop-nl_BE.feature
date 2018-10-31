@DWP
@E2E
@B2C
@CREDIT-AND-CONTROL
@NUAT-5019
Feature: NUAT-5019-6: End order (drop) messaging

    Background:
        Given I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario: Create end order (drop) market message for a customer
        When Left menu is contracting-switching
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2C"
        And Label input for "Type klant" is "CUSTOMER"
        Then View list header is "Klanten"

        When Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Marktberichten
        Then View list header is "Marktberichten"
        And 1st List element with value at column "EAN-code & Producttype" is checked

        When Click on Start nieuw marktbericht
        And Click Select Contractline
        And  Dialog search input is current "parameter:EAN-code & Producttype"
        Then Select Contractline dialog is confirmed

        When "Module" selection is "INITIATE STOP ACCESS"
        And Label input for "Label" is "Drop/Request Budget Meter"
        And Option "Testing?" is On
        And Select Contractline dialog is confirmed
        Then 1st list element has cell value INITIATE STOP ACCESS at column Module & Label
