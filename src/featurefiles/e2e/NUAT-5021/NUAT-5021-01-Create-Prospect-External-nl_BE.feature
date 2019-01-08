@DWP
@B2C
Feature: NUAT-5021 Step 1. Create an account for de-duplication.

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    @ONBOARDING-EXTERNAL
    @NUAT-5021-STEP-1
    Scenario: Create an electricity only contract quote
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
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
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Electricity EAN code is "random"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Quote is confirmed
        Then View list header is "Offertes"
        And 1st list element has cell value Sales Verstuurd naar de klant - Geaccepteerd at column Type & status

        When Dashboard menu is Marktberichten
        Then View List is empty

        When Top arrow button is Up
        And Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "parameter:suitecrm-customer-name"

        Given 1st List element with value at column "Klantnummer & Naam" is checked
        And External status is "On" for SuiteCRM Customer Number "parameter:Klantnummer & Naam"

