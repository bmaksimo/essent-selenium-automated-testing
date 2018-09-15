@CONSUMPTION
Feature: Received consumption

    Background:
        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Generate consumption for settlement and verify in DWP
    # 1. generate consumption for deliverypointid 541448820045083585 temporarily (TODO)
    When Consumption at deliverypointid 541448820045083585 with NIGHT_EXCLUSIVE hourly-tariff is generated from now until 6 months after
        And Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2C"
        And "Type klant" input in list is "CUSTOMER"
        # temporarily testing klant 1000000000 (TODO)
        And "Klantnummer" input is "1000000000"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        When Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
#        And 1st list element has cell value Sales Getekend - Geaccepteerd at column Type & status
