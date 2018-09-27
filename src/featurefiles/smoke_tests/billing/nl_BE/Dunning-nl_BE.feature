@BILLING
@DUNNING
@SMOKE

Feature: Billing - Dunning

#    Background:

    Scenario: Create HB1 letter and verify its creation in DWP    Scenario: Generate consumption for settlement and verify in DWP
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2C"
        And Label input for "Type klant" is "CUSTOMER"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Contracten
        Then View list header is "Actieve en toekomstige connecties"
        And 1st List element with value at column "EAN-code" is checked

        When Consumption at current deliverypointid with NIGHT_EXCLUSIVE hourly-tariff is generated from now until 6 months after
        And Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
        And View list header is "Verbruiken"
        Then Consumption is available at 1st row in Van - Aan column

        When Dunning is advanced for 5 day(s)
