@REGRESSION
@DWP
@B2C
@ALL
Feature: TESTAUTO-116-B2C guarantee sign in

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-116
    Scenario: B2C guarantee sign in
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
        And Get Account Number
        Then "1st" list element has cell value "Sales Verstuurd naar de klant - Geaccepteerd" at column "Type & status"

        #2.1 Set up guarantee amount
        When Plus action of "1" element from "QuotesOnAccount" and click on "Wijzig KA status"
        And "Klantacceptatie" selection is "Waarborg"
        And "Waarborgbedrag" input is "200"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Verstuurd naar de klant - Waarborg" at column "Type & status"

         #2.1 Sign quote
        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum ondertekening" date is "now"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Handtekening ontvangen - Waarborg" at column "Type & status"

        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        And Changes are confirmed
        Then "1st" list element has cell value "Sales Getekend - Waarborg" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then  "1st" list element has cell value "Te activeren" at column "Contractnummer" polling 550 seconds

        #2.3 Check guarantee advance invoice total amount as sum of electricity and gas advance amounts.
        When Dashboard menu is "Service"
        Then List element matching value "Outbound document: guarantee" at column "Type & Onderwerp" from table "Interacties" is checked

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (GUARANTEE)" at column "ID & Type"
        And "1st" list element has cell value "200" at column "Bedrag" polling 10 seconds

        #2.2 Check invoice due date, should be 19 days after transaction date.
        And "1st" list element with date interval at column "Datum & Vervaldatum" from table "Transacties" is "19 days"
