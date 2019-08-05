@REGRESSION
@DWP
@B2B
@ALL

Feature: TESTAUTO-56:Electricity-UP-Electricity-fix-TK2-MMR

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-56
    Scenario: Checking prices Electricity-UP-Electricity-fix-TK2-MMR
        When Plus menu is "Sales -> UP/TK2 -> Pricing tool"
        Then Form header is "Calculate price"

        When "Producttype" selection is "Elektriciteit"
        And "Tariefgroep" selection is "TK2"
        And "Product" selection is "Elektriciteit Vast TK2"
        And "Startdatum" date is "14 days from now"
        And "Duurtijd" selection is "24 Months"

        When "Tariefdatum" date is "now"
        And "Type aansluiting" selection is "MMR"
        And "Type meter" selection is "Enkelvoudig"
        And "Verbruik enkelvoudig (kWh)" input is "10000"
        Then Table price calculation is not empty
