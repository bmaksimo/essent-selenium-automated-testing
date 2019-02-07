@REGRESSION
@DWP
@B2C
@REGRESSION
@J
Feature: NSTA - 388 Move new address

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    Scenario: Move new address
#        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
#        Then Form header is "Quote details"
#
#        When "Tariefdatum" date is "1 month before now"
#        And "Sales kanaal" selection is "Inbound"
#        And Quote details are confirmed
#        Then Form header is "Personal details"
#
#        When Customer is random
#        And Customer address is
#            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
#            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
#        And Customer details are confirmed
#        Then Form header is "Select package & fuel type"
#
#        Then Form header is "Select package & fuel type"
#
#        When Package is "Vast"
#        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
#        And Package and Fuel Type is confirmed
#        Then Form header is "Connection details"
#
#        When "Startdatum" date is "2 weeks before now"
#        And Electricity EAN code is "random"
#        And Option "test" is On
#        And Option "MM should respond?" is On
#        Then Form header is "Billing details"
#
#        When "Betalingswijze" selection is "Overschrijving"
#        And  Billing details are confirmed
#        Then  Form header is "Quote overview"
#
#        When Quote is confirmed
#        Then View list header is "Offertes"
#        And "1st" list element has cell value "Sales Verstuurd naar de klant - Geaccepteerd" at column "Type & status"
#
#        When Dashboard menu is "Marktberichten"
#        Then View List is empty
##
#        When Top arrow button is "Up"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Filter button is clicked
#        And Top action is "Filters"
        And "Klantnummer" input is "1000077688"
#       1000071209,1000077671 uat8  1000055291 ,1000055447

#        And "Naam" input is "parameter:suitecrm-customer-name"

        When Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is "Contracten"
        When Old contract data is copied
        And Dashboard menu is "Contracten"
        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis NA"
#        And Customer address is
#            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
#            | Verbindingsstraat | 63     |            |     | 9220       | HAMME   |         |

        And New move customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Verbindingsstraat | 63     |            |     | 9220       | HAMME   |         |

        And "Startdatum verhuis" date is "1 day before now"


        And "Is de meter geopend" turn on
        And Select EAN
        And "Datum meteropname" date is "1 day before now"
        And "Meterstand enkelvoudig" input is "1000"
        And Option "test" is on
#        And Option "MM should respond" is on
        And "MM should respond" turn on
#        And Bevestigen
        And Changes are confirmed

        And Get Contract Ean Code

        When Old contract data is copied
#        Then Check if contract with old ean is still active
#        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"
#    	And Check if contract with new ean is created and became active
#    	And Check if start date of new ean is the same date as filled in as “Move date”
#    	And Check if the end date of new ean is the same date as the end date of the old one
#        And Check if products of both contracts are the same
#        And Check if discounts of both contracts are the same
#        And Check if prices of both contracts are the same
        
        And Top arrow button is "back"
        When Dashboard menu is "Marktberichten"
        Then Marketbericht with EAN "parameter:contractEanCode" and module "START ACCES" is in status "GESLOTEN"
        And Marketbericht with EAN "parameter:contractEanCode" has ED "1 day before now"

        When Dashboard menu is "Service"
        Then There is a case where onderwerp is "VERHUIS"
        And  Interaction is created with Type "Document" and Onderwerp "Outbound document: Old inhabitant remains customer" and verwante case is "parameter:caseNumber"


