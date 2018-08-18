@DWP
@BASIC
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote - supplier switch

    Background:
        Given I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario: Create a B2C Quote with Supplier Switch
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> TC1 -> Create new quote B2C"
        Then Form Header is "Quote details"

        When B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form Header is "Personal details"

        When Customer is random
        And Customer Address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 146    |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form Header is "Select package & fuel type"

        When Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form Header is "Connection details"
        And No price sheet alerts popped up

        When "Start date" date input is "$today"
        And Electricity EAN code is selected
        And Connection details are confirmed
        Then Form Header is "Billing details"

        When Payment details are: method Direct Debit, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Billing details are confirmed
        Then  Form Header is "Quote overview"

        When Quote is signed in Kontich
        And Quote is confirmed
        And View List Header is "Quotes"
        Then Select 1 List rows having cell value Sales Signed - Accepted at column Type & Status
