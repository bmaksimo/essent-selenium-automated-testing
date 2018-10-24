@DWP
@E2E
@CREDIT-AND-CONTROL
@CONSUMPTIONS
@NUAT-5019
Feature: Received consumption

    Background:
        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario: Generate consumption for given customer, and verify the result in DWP

        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-custoner-name"
        #And "Naam" input is "Bowe te braak"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And 1st List element with value at column "EAN-code" is checked

        When Consumption at current deliverypointid with NIGHT_EXCLUSIVE hourly-tariff is generated from now until 6 months after
        And Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        And View list header is "Verbruiken"
        Then Consumption is available at 1st row in Van - Aan column
