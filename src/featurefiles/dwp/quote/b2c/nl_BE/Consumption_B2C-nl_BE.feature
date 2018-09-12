@CONSUMPTION
Feature: Received consumption

    Background:
        Given I logged in to DWP as billing.testautomation@essent.be

    Scenario: Generate consumption for settlement and verify in DWP
    # 1. generate consumption for klant 1000000000 temporarily (TODO)
        When Consumption is generated
    # 2. check if consumption is generated in DWP
        And Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2C"
        And "Type klant" input is "CUSTOMER"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"

        When Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        Then View list header is "Verbruiken"
        # TODO - and consumption on <specified date> is available
