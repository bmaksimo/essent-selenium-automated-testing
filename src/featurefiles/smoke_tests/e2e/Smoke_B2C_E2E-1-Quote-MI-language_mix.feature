@DWP
@SMOKE
@REGRESSION
Feature: Creating a B2C Quote TC1 with move in - Dutch version
    The test creates a contract with start date and pricing date set to 3 months before now.
    The existing valid address not known to Nova is used.
    The new Dutch-speaking customer is generated randomly,
    with pronounceable first and last name and valid date of birth.
    DWP application Closes the electricity meter, and  MIG from supplier switch to customer switch automatically.

    Background:
        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    Scenario: Create a B2C Quote with customer switch
        When Top action is Plus Menu
        And Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        #Then Form header is "Details van de offerte"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        #Then Form header is "Persoonsgegevens"
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2   |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "now"
        And Electricity EAN code is selected
        And Electricity meter is Closed
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"


        When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And Quote is signed in Kontich
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"

        When Plus actions at 1st list row having cell value "Sales Handtekening ontvangen - Geaccepteerd" at column "Type & status" are open
        And List plus action is Bevestig
        And Modal dialog is Sign quote
        And  Contract signature is confirmed
        Then 1st list element has cell value Sales Getekend - Geaccepteerd at column Type & status
        And  Number parameter "id-billing-customer" is put from "1st" row and "Billing klant & Tariefdatum" column

        
    
