@DWP
@BASIC
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote - supplier switch

    Background:
        Given I logged in to DWP as BusinessDeskB2C

    Scenario: Create a B2C Quote with Supplier Switch
        When Top action is Plus Menu
        And Plus menu is "Sales -> TC1 -> Create new quote B2C"
        Then Form header is "Quote details"
        And  Default B2C channel info is confirmed
        When Customer details are random
        And Package is "TC_FIX_B2C"
        And Price sheet alert doesn't pop up
        And Electricity and gas meter number and EAN are:
            | productType | meterNumber | ean                |
            | Electricity | 1331710     | 541448820045964198 |
            | Gas         | 016258425   | 541448820045964198 |
        And Connection is confirmed
        And Payment details are: method BankTransfer, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
        And Quote is confirmed
        And View list header is "Quotes"
        #Bugfix in UAT02 pending: removal extra space between Sales  Sent
        Then Select 1 List rows having cell value Sales  Sent to customer - Accepted at column Type & Status
