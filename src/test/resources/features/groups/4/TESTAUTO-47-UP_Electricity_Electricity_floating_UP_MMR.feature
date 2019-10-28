@DWP
@B2B
@REGRESSION
@ALL
Feature: TESTAUTO-47 Electricity floating UP – MMR

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-47
    Scenario: Checking prices "Electricity floating UP – MMR    "
        When Click on top menu button PLUS and navigate to "Sales -> UP/TK2 -> Pricing tool"
        Then Form header is "Calculate price"

        When "Producttype" selection is "Elektriciteit"
        And "Type meter" selection is "Enkelvoudig"
        And "Tariefgroep" selection is "UP"
        And "Product" selection is "Elektriciteit Variabel"
        And "Startdatum" date is first day of next month
        And "Einddatum" date is last day of current month next year

        When "Tariefdatum" date is "now"
        And "Type aansluiting" selection is "MMR"

        And "Verbruik enkelvoudig (kWh)" input is "3000"
        And Huidige waarde is not empty
        And Indexatieparameter is not empty
        And Table price calculation is not empty
