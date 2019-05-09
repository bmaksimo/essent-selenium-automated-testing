@DWP
@SALES-MARKETING
@QUOTE-MI
@DEV
Feature: Creating a B2C Quote TC1 with move in - Dutch language version

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    Scenario: Create a B2C Quote with move in
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Details van de offerte"

        When B2C sales channel is "Inbound"
        And Quote details are confirmed
        Then Form header is "Persoonsgegevens"

        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 21   |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Selecteer pakket en product"

        When Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connectiedetails"
        And Price sheet alert doesn't pop up

        When "Startdatum" date is "now"
        And Electricity EAN code is selected
        And Electricity meter is Closed
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Facturatiedetails"

        When Payment details are: method "Overschrijving", IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Overzicht offerte"

        When Option "Heeft de klant al getekend?" is On
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"

        When Plus actions at "1st" list row having cell value "Sales Handtekening ontvangen - Geaccepteerd" at column "Type & status" are open
        And List plus action is "Bevestig"
        And Modal dialog is "Sign quote"
        And  Contract signature is confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"
