@DWP
@REGRESSION
@ALL
Feature: TESTAUTO-109: Create B2B TK2 Contract with Online Signing

    Background:
    Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @TESTAUTO-109
    Scenario: TESTAUTO-109: Create B2B TK2 Contract with Online Signing
        When Plus menu is "Sales -> UP/TK2 -> Creëer nieuwe offerte (B2B)"
        Then Form header is "Perform customer acceptance check"

        Given Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
        And Value at "Klantacceptatie" in the card "Perform customer acceptance check" is "Geaccepteerd"
        When Customer acceptance checks page is confirmed
        Then Form header is "Contact person"

        Given "Rechtsvorm" selection is "bvba"
        And Select Nace-Code
        And NaceCode in search is 01120 - Teelt van rijst
        And Field "First name" input is "parameter:contact-person-first-name"
        And Field "Last name" input is "parameter:contact-person-last-name"
        And Company address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |
        And Company contact info is generated
        When Customer details are confirmed
        Then Form header is "Connection details"

        Given Option "test" "is" "On"
        And "Producttype" selection is "Elektriciteit"
        And EAN code is generated
        And "Type aansluiting" selection is "YMR"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "30301267ISK"
        When Connection details are confirmed
        And Save changes
        Then Form header is "Pricing details"

        Given "Tariefgroep" selection is "TK2"
        And "Product" selection is "Elektriciteit Vast TK2"
        And "Startdatum" date is "5 days before now"
        And "Verbruik enkelvoudig (kWh)" input is "5000"
        And "Duurtijd" selection is "24 Months"
        When Pricing details are confirmed
        Then Form header is "Billing details"

        Given "Betalingswijze" selection is "Overschrijving"
        Then Quote is confirmed

  #2.1 Sign quote

        Given Dashboard menu is "Sales"
        And Plus action of "1" element from "QuotesOnAccount" and click on "Verzenden naar klant"
        And Option "Mij een e-mail sturen?" "is" "On"
        When Changes are confirmed
        Then "1st" list element has cell value "Verstuurd naar de klant - Geaccepteerd" at column "Type & status"

        Given Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        When Changes are confirmed
        Then "1st" list element has cell value "Handtekening ontvangen - Geaccepteerd" at column "Type & status"

        Given Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        And Quote for account is signed online in modal
        When Changes are confirmed
        Then "1st" list element has cell value "Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        And "1st" List element with value at column "EAN-code" is checked
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 1200 seconds
