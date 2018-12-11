@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL

Feature: NUAT-412 part: Create TK1 Contract
@NUAT-412
    Background:
        Given  I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario: Create active contract TK1
        When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
        And "Ondernemingsnummer" input is "BE0659881595"
        And "Bedrijfsnaam" input is "Test Company B2B"
        And Clicked on sign X
        And New Quote is saved

        When "Tariefdatum" date is "now"
        And B2B sales channel is Inbound
        And Quote details are confirmed

        When Rechtsvorm is bvba
        And First Name is Test and Last Name is "Test B2B"
        And BEDRIJFSNAAM is Test Company B2B
        And Telefoon is +3232331231
        And Geslacht is Male
        And E-mailadres is test@test.com
        And Select Nace-Code
        And NaceCode in search is 01120 - Teelt van rijst
        And Customer Details are populated with: Address is Veldkant and HouseNumber is "2" and PostalCode is "2550" and City is "KONTICH"
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2B (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "now"
        And Ean-Code is 541448810000064421
        And Connection details are confirmed
        Then Form header is "Billing details"

        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then 1st list element has cell value Sales Getekend - Waarborg at column Type & status
