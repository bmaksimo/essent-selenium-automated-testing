@DWP
@REGRESSION
@CREDIT-AND-CONTROL
@UNSTABLE
Feature: NUAT-412 part: Create / import coda file

    @NUAT-412-2-STABILIZE
    Scenario: Create active contract TK1
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
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

        #When "Startdatum" date on "Elektriciteit Vast" card is "35 days before now"
        When Option "test" is On
        And EAN code is generated
        And "EAN-code" input on "Elektriciteit Vast" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"














