@REGRESSION
@DWP
@B2C
@ALL
Feature: TESTAUTO-117-B2C online sign in

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-117
    Scenario: B2C online sign in
        #Create an active contract
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        And "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        Then Customer details are confirmed

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        Then Package and Fuel Type is confirmed

        When "Startdatum" date is "35 days before now"
        And Electricity EAN code is "random"
        And "Type aansluiting" selection is "YMR"
        And "Meternummer" input is "1000"
        And Option "test" "is" "On"
        And Connection details are confirmed
        Then Save changes

        When "Betalingswijze" selection is "Overschrijving"
        Then  Billing details are confirmed

        When "Kanaal ondertekening" selection is "Online"
        And Quote is confirmed
        Then "1st" list element has cell value "Sales Verstuurd naar de klant - Geaccepteerd" at column "Type & status"

         #2.1 Sign quote
        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum ondertekening" date is "now"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Handtekening ontvangen - Geaccepteerd" at column "Type & status"

        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
