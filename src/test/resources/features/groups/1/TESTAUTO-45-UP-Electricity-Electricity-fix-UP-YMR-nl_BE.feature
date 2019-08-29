@REGRESSION
@DWP
@B2B
@ALL

Feature: TESTAUTO-45:Electricity-UP-Electricity-fix-UP-YMR

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-45
    Scenario: Checking prices "Electricity – UP – Electricity fix UP -YMR"
        When Plus menu is "Sales -> UP/TK2 -> Pricing tool"
        Then Form header is "Calculate price"

        When "Producttype" selection is "Elektriciteit"
        And "Tariefgroep" selection is "UP"
        And "Product" selection is "Elektriciteit Vast"
        And "Startdatum" date is first day of next month
        And "Einddatum" date is last day of current month next year


        When "Tariefdatum" date is "now"
        And "Type aansluiting" selection is "YMR"
        And "Type meter" selection is "Enkelvoudig"
        And "Verbruik enkelvoudig (kWh)" input is "10000"
        Then Table price calculation is not empty
