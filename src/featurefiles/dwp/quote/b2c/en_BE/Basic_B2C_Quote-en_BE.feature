@DWP
@BASIC
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote - moveIn

  Background:
      Given I logged in in DWP as BusinessDeskB2C
      Given I optionally discard a previous flow:

  Scenario: Create a B2C Quote
    When Top Action is Plus Menu
    And Plus Menu is "Sales -> TC1 -> Create new quote B2C"
    Then Form Header is "Quote details"
    And  Confirm Default B2C Channel Info
    When Customer details are random
    And Package is "TC_FIX_B2C"
    And No price sheet alerts popped up
    #And Electricity and gas meter numbers and their EANs are:
    #     |productType |meterNumber |ean                |
    #     |Electricity |1331710     |541448820045964198 |
    #     |Gas         |016258425   |541448820045964198 |
    #And Confirm "Connection" details
    #And Payment details are: method: BankTransfer, IBAN: "NL57ABNA0874253356" and bic: "123":
    #Then View List Header is "Account"
    #When I select the 1st element and click the link in the "Number & Signed contract nr" column
    #Then A quote with type "Sales" and status "Signed - Accepted" is created
