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
        And B2C sales channel is Inbound
        And  Default B2C Channel Info is confirmed
        When Customer is random
        And Customer Address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 140    |            |      | 2550       | Kontich  |         |
        And Package is "TC_FIX_B2C"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        And No price sheet alerts popped up
        And "Start date" date input is "$today"
        And Electricity EAN code is selected
        And Connection is confirmed
        And Payment details are: method BankTransfer, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Quote is signed in Kontich
        And Quote is confirmed
        And View List Header is "Quotes"
        #Bugfix in UAT02 pending: removal extra space between Sales  Sent
        Then Select 1 List rows having cell value Sales Signed - Accepted at column Type & Status
