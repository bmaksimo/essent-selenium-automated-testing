@DWP
@BASIC
@_QUOTE
@REGRESSION
Feature: Creating a B2C Quote - moveIn

  Background:
      Given I logged in in DWP as essentadmin
      Given I optionally discard a previous flow:

  Scenario: We can create a B2C Quote
      When Top Action is Plus Menu
      And Plus Menu is "Sales -> TC1 -> Create new quote B2C"
      Then Form Header is "Quote details"
      And  B2C sales channel is Inbound
      And  Customer details for B2C are:
       |firstName       |lastName               |gender|birthDate |phoneNumber|mobile      |email                  |language|street       |houseNr|houseNrAdd |postalCode|city   |
       |Jim             |van D${TIMESTAMP}      |Mr.   |09/10/1966|           |+32486761271|jim@billinghouse.nl    |NL      |Vijgenstraat |3      |           |9160      |Lokeren|
      # TC_${MM_yyyy}_B2C
      And Tariffsheet and package are:
        |tariffSheet    |packageName  |
        |TC_04_2018_B2C |MIG_FLIX_HEAT|
      And No price sheet alerts popped up
      And Electricity and gas meter numbers and their EANs are:
         |productType |meterNumber |ean                |
         |Electricity |1331710     |541448820045964198 |
         |Gas         |016258425   |541448820045964198 |
      And The Electricity meter is Closed
      And The Gas meter is Closed
      And Confirm "Connection" details
      And Payment details are: method: BankTransfer, IBAN: "NL57ABNA0874253356" and bic: "123":
      And Signing contract on date: DWP_TODAY in "Antwerpen" with hand signature file "/data/dwp/signed-document.pdf":
      Then Redirect view is "Account" with first name: "firstName" and last name: "lastName" customer details entry
      When I select the 1st element and click the link in the "Number & Signed contract nr" column
      Then A quote with type "Sales" and status "Signed - Accepted" is created
