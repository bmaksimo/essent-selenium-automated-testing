@DWP
Feature: DROP for a Non-residential client

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: DROP for a Non-residential client
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds


        When Top action is Filters
        And "Naam" input is "%Steven%"
        And "Klantnummer" input is "150638828"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is Marktberichten
        And Select "START NIEUW MARKTBERICHT" on Marktberichten page
        And Changes are confirmed
        When Input in Module is "INITIATE STOP ACCESS"
        And Input in Label is "Drop/Request Budget Meter"
        And Check toggle "Testing"
        And Check toggle "Market mocK"
        And Changes are confirmed
        Then Confirm task was "Non-Residential Drop"


