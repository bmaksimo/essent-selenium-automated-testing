@DWP
@REGRESSION
@B2B
@BUSINESS-DESK
Feature: NUAT-479: DROP For A Non Residential Client

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: DROP for a Non-residential client
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "1000015412"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 15 seconds

        When Dashboard menu is Contracten
        And Find "Actief" contract
        And Dashboard menu is Marktberichten
        And Click on Start nieuw marktbericht
        And Click Select Contractline
        And Search for ean code
        Then Changes are confirmed

        When Input in Module is "INITIATE STOP ACCESS"
        And Input in Label is "Drop/Request Budget Meter"
        And Check toggle "Testing"
        And Check toggle "Market mocK"
        And Changes are confirmed
        Then Confirm task was "Non-Residential Drop"


