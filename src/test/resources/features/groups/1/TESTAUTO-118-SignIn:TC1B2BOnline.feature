@DWP
@REGRESSION
@CREDIT-AND-CONTROL
@ALL
Feature: TESTAUTO-118: Sign in: TC1 B2B Online

    Background:
        Given  I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-118
    Scenario: Sign in: TC1 B2B Online
        When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
        And Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Value at "Klantacceptatie" in the card "Perform customer acceptance check" is "Geaccepteerd"
        And Customer acceptance checks page is confirmed
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Contact person"

        When "Rechtsvorm" selection is "bvba"
        And Select Nace-Code
        And NaceCode in search is 01120 - Teelt van rijst

        And Field "First name" input is "parameter:contact-person-first-name"
        And Field "Last name" input is "parameter:contact-person-last-name"

        And Company address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |

        And Company contact info is generated
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2B (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Option "test" "is" "On"
        And EAN code is generated
        And "Startdatum" date on "Elektriciteit Vast" card is "now"
        And "EAN-code" input on "Elektriciteit Vast" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

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
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
