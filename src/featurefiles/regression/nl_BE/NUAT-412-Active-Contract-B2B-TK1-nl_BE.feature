@DWP
@REGRESSION
@CREDIT-AND-CONTROL
@ALL
Feature: NUAT-412 part: Create B2B TK1 Contract

    Background:
        Given  I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NUAT-412
    Scenario: Create active contract TK1 ->
        When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
        And Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
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

        When Option "test" is On
        And EAN code is generated
        And "Startdatum" date on "Elektriciteit Vast" card is "now"
        And "EAN-code" input on "Elektriciteit Vast" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds
