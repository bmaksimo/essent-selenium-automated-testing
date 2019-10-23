@REGRESSION
@DWP
@B2B
@ALL

Feature: TESTAUTO-57:Electricity-TC2-Electricity-fix-TC2-YMR

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-57
    Scenario: Checking prices "Electricity-TC2-Electricity-fix-TC2-YMR"
        When Click on top menu button PLUS and navigate to "Sales -> UP/TK2 -> Pricing tool"
        Then Form header is "Calculate price"

        When "Producttype" selection is "Elektriciteit"
        And "Tariefgroep" selection is "TK2"
        And "Product" selection is "Elektriciteit Vast TK2"
        And "Startdatum" date is "14 days from now"
        And "Duurtijd" selection is "24 Months"


        When "Tariefdatum" date is "now"
        And "Type aansluiting" selection is "YMR"
        And "Type meter" selection is "Enkelvoudig"
        And "Verbruik enkelvoudig (kWh)" input is "10000"
        Then Table price calculation is not empty
