@DWP
@QUOTE_CS
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote with customer switch - Dutch version
         The test creates a contract with start date and pricing date set to 3 months before now.
         The existing valid address already known to Nova is used,
         the new Dutch-speaking customer is generated randomly,
         with pronounceable first and last name and valid date of birth.
         DWP application switches MIG from supplier switch to customer switch automatically.

    Background:
        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario: Create a B2C Quote with customer switch
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form Header is "Details van de offerte"

        When B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form Header is "Persoonsgegevens"

        When Customer is random
        And Customer Address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 13   |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form Header is "Selecteer pakket en product"

        When Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form Header is "Connectiedetails"
        And No price sheet alerts popped up

        When "Startdatum" date is "$today"
        And Electricity EAN code is selected
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form Header is "Facturatiedetails"

        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form Header is "Overzicht offerte"

        When Option "Heeft de klant al getekend?" is On
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "$today"
        And Quote is confirmed
        Then View List Header is "Offertes"
        And 1 List row having cell value Sales Handtekening ontvangen - Geaccepteerd at column Type & status is selected
