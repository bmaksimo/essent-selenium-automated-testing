@REGRESSION
@DWP
@B2C
@ALL
Feature: TESTAUTO - 25 Active contract with DOM

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-25
    Scenario: Active contract with DOM
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Electricity Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When EAN code is generated
        And "Startdatum" date is "5 day before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "1000"
        And Option "test" "is" "On"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"
        When "Betalingswijze" selection is "Domiciliëring"
        And IBAN is generated
        And "IBAN" input is "parameter:iban"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Quote is signed
        And Quote is signed in "Kontich"
        When Quote is confirmed

        When Dashboard menu is "Contracten"
        And  "1st" List element with value at column "EAN-code" is checked
        And Get Account Number
        And Copy product name
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        When Dashboard menu is "Details"
        Then Customer bank number is "parameter:iban" and payment method is "Domiciliëring (Aangevraagd)"

        Given I renew login to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is Customers
        And Advanced search is
            |     field      |   operator  |          value          |
            | Account Number | is equal to | parameter:accountNumber |
        And Column "Account Number" with value "parameter:accountNumber" is clicked
        And Odoo click on tab "Accounting"
        And Odoo validate bank account was changed on "parameter:iban"
        And Odoo click on account number
        Then Odoo check if bank account checkbox 'active' is checked
        And Odoo check if format is "Sepa Mandate"
        And Odoo check if send to customer checkbox is checked
        And Odoo check if send date is today

