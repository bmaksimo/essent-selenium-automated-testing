@DWP
@BASIC
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote - supplier switch

    Background:
        Given I logged in in DWP as BusinessDeskB2C
        Given I optionally discard a previous flow

    Scenario: Create a B2C Quote with Supplier Switch
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> TC1 -> Create new quote B2C"
        Then Form Header is "Quote details"
        And  Confirm Default B2C Channel Info
        When Customer details are random
        And Package is "TC_FIX_B2C"
        And No price sheet alerts popped up
        And Electricity and gas meter numbers and their EANs are:
            | productType | meterNumber | ean                |
            | Electricity | 1331710     | 541448820045964198 |
            | Gas         | 016258425   | 541448820045964198 |
        And Confirm Connection
        And Payment details are: method BankTransfer, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Confirm Quote
        And View List Header is "Quotes"
        #Bugfix in UAT02 pending: removal extra space between Sales  Sent
        Then Select 1 List rows having cell value Sales  Sent to customer - Accepted at column Type & Status
