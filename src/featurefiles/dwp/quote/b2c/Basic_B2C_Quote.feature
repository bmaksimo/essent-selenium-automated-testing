@DWP
@BASIC
@QUOTE
@REGRESSION
Feature: Creating a B2C Quote

  Background:
      Given I logged in as admin on the DWP Main Page
      Given I optionally discard a previous flow:

  Scenario: We can create a B2C Quote
    When I start a B2C quote flow:
    And I select Inbound sales channel and accept standard quote type for B2C:
    And I enter customer details for B2C:
       |firstName       |lastName               |gender|birthDate |phoneNumber|mobile      |email                  |language|street       |houseNr|houseNrAdd |postalCode|city   |
       |Jim             |van D${TIMESTAMP}      |MISTER|09/10/1966|           |+32486761271|jim@billinghouse.nl    |NL      |Vijgenstraat |3      |           |9160      |Lokeren|
    And I select tariffsheet and package:
        |tariffSheet       |packageName  |
        |TC_${MM_yyyy}_B2C |MIG_FLIX_HEAT|
    And I don't detect any price sheet alerts
    And I fill in the electricity and gas meter numbers and their EANs respectively:
         |productType |meterNumber |ean                |
         |Electricity |1331710     |541448820045964198 |
         |Gas         |016258425   |541448820045964198 |
    And I optionally Close the Electricity meter
    And I optionally Close the Gas meter
    And I confirm Connection details
    And I select payment method BankTransfer for IBAN "NL57ABNA0874253356" and bic "123":
    And I sign on date DWP_TODAY in location "Antwerpen" with file "/data/dwp/signed-document.pdf":
    Then The system redirects me to account page that has card with "firstName" and "lastName" customer details that I filled in
    When I select the 1st element and click on the link in the Number & Signed contract nr column
    #And A quote with type "Sales" and status "Signed - Accepted" is created
