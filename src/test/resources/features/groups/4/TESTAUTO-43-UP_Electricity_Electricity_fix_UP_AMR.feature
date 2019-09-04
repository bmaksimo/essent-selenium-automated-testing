@DWP
@B2B
@REGRESSION
@ALL
Feature: TESTAUTO-43 Up Electricity fix UP AMR
    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-43
    Scenario: Checking prices "Electricity – UP – Electricity fix UP -AMR"
        When Plus menu is "Sales -> UP/TK2 -> Pricing tool"
        Then Form header is "Calculate price"

        When "Producttype" selection is "Elektriciteit"
        And "Type meter" selection is "Tweevoudig"
        And "Tariefgroep" selection is "UP"
        And "Product" selection is "Elektriciteit Vast"
        And "Startdatum" date is first day of next month
        And "Einddatum" date is last day of current month next year

        When "Tariefdatum" date is "now"
        And "Type aansluiting" selection is "AMR"

        And "Verbruik dag (kWh)" input is "3000"
        And "Verbruik nacht (kWh)" input is "3000"
        And "kW Max" input is "20"
        And Table price calculation is not empty
