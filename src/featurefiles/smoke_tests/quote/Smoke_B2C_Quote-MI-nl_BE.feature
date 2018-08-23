@DWP
@BASIC
@_QUOTE
@REGRESSION
Feature: Creating a B2C Quote - supplier switch

    Background:
        Given I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario: Create a B2C Quote with Supplier Switch
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form Header is "Details van de offerte"

        When "Tariefdatum" date is "$today - 3 months"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form Header is "Persoonsgegevens"

        When Customer is random
        And Customer Address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 12   |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form Header is "Selecteer pakket en product"

        When Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form Header is "Connectiedetails"
        And No price sheet alerts popped up

        When "Startdatum" date is "$today - 3 months"
        And Electricity EAN code is selected
        #And Electricity meter is Closed
        #And Option "Is de meter geopend?" is Closed
        And Connection details are confirmed
        Then Form Header is "Facturatiedetails"

        When Payment details are: method Domiciliëring, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form Header is "Overzicht offerte"

        When Option "Heeft de klant al getekend?" is On
        And "Datum ondertekening" date is "$today - 3 months"
        And Quote is signed in Kontich
        And Quote is confirmed
        Then View List Header is "Offertes"
        And  1 List row having cell value Sales Getekend - Geaccepteerd at column Type & status is selected


