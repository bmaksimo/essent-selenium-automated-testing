@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL

Feature: NUAT-412: Import and match CODA file (account creation, invoice run, creation coda and import ocda)

 Background:
 Given  I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

 Scenario: Create active contract TK1
     When Plus menu is "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
     And "Ondernemingsnummer" input is "BE0659881595"
     And "Bedrijfsnaam" input is "Test New B2B"
     And Clicked on sign X
     And New Quote is saved

     When "Tariefdatum" date is "now"
     And B2B sales kanaal is Inbound
     And Quote details are confirmed

     When Rechtsvorm is bvba
     And ContactPersoon is Test and "Test B2B"
     And BEDRIJFSNAAM is Test New B2B
     And Telefoon is +3232331231
     And Geslacht is Onbekend
     And E-mailadres is test@test.com
     And Select Nace-Code
     And NaceCode in search is 01120 - Teelt van rijst
     And Customer Details are populated with: Address is Veldkant and HouseNumber is "2" and PostalCode is "2550" and City is "KONTICH"
     And Customer details are confirmed
     Then Form header is "Select package & fuel type"

     When Package is "Vast"
     And Checkbox "Gas Fix B2B (TC1)" is Unchecked
     And Package and Fuel Type is confirmed
     Then Form header is "Connection details"

     When "Startdatum" date is "now"
     And Ean-Code is 541448810000064421
     And Connection details are confirmed
     Then Form header is "Billing details"

     When Payment details are: method Overschrijving, IBAN "NL57ABNA0874253356", bic "ABNANL2A"
     And Billing details are confirmed
     Then  Form header is "Quote overview"

     When Option "Heeft de klant al getekend?" is On
     And "Kanaal ondertekening" selection is "Papier"
     And Quote is signed in Kontich
     And "Datum ondertekening" date is "now"
     And Quote is confirmed
     Then View list header is "Offertes"
     Then 1st list element has cell value Sales Getekend - Waarborg at column Type & status
















#    Scenario: Generate consumption for given customer, and verify the result in DWP
#
#        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be
#
#        When Left menu is sales-marketing
#        And Top menu item is Klanten
#        And Top action is Filters
#       And "Klantnummer" input is "1000026194"
#        Then 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
#
#        Given Click on link in View List at 1st row and "Klantnummer & Naam" column
#        When Dashboard menu is Contracten
#        Then View list header is "Actieve en toekomstige connecties"
#        And 1st List element with value at column "EAN-code" is checked
#
#        Given Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
#        Then View list header is "Verbruiken"
#        And  Verbruiken list is empty
#
#        Given Top arrow button is Back
#        When Consumption at deliverypointid parameter:EAN-code is generated from now until 2019-09-30
#        And Click on link in "Actieve en toekomstige connecties" View List at 1st row and "EAN-code" column
#        Then View list header is "Verbruiken"
#        And Consumption is available at 1st row in Van - Aan column



#    Scenario: Trigger Invoice run process
#
#    Given I logged in to DWP as billing.testautomation@essent.be
#
#    When Left menu is billing
#    And Top menu item is Klanten
#    And Top action is Filters
#    And "Klantnummer" input is "1000026194"
#
#    Given 1st List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
#    And 1st List element with value at column "Klantnummer & Naam" is checked
#    And Click on link in View List at 1st row and "Klantnummer & Naam" column
#    And Dashboard menu is Contracten
#    Then View list header is "Actieve en toekomstige connecties"
#    And 1st List element with value at column "EAN-code" is checked
#
#    Given Top arrow button is Up
#    And Plus menu is "Billing -> Start facturatierun"
#    When Modal dialog is Start invoicerun
#    And "Naam job" selection is "recurrent"
#    And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
#    And "Factuurdatum" date is "now"
#    And "Procesdatum" date is "1 month from now"
#    Then Invoice run is scheduled
#
#    Given Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
#    When Dashboard menu is Billing
#    Then View list header is "Transacties"
#    And 1st list element has cell value Invoice (ADVANCE) at column ID & Type
