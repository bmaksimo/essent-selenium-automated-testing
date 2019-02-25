@REGRESSION
@DWP
@B2C
@REGRESSION

Feature: NSTA - 388 Move new address

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-338
    Scenario: Move new address
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

#        When "Startdatum" date is "2 weeks before now"
        When "Startdatum" date is "5 day before now"
        And Electricity EAN code is "random"
        And "Meternummer" input is "1000"
        And Option "test" is On
        And Bevestigen
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"

        And Quote is signed
        And Bevestigen
        And Sign
        And Changes are confirmed

        When Dashboard menu is "Marktberichten"
        Then View List is empty

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And  "1st" List element with value at column "EAN-code" is checked
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds






#
#        When Left menu is "sales-marketing"
#        And Top menu item is "Klanten"
#        And Filter button is clicked
#        And "Klantnummer" input is "1000098514"
#
#
##        And "Naam" input is "parameter:suitecrm-customer-name"







        When Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is "Contracten"


        When Old contract data is copied
        And Dashboard menu is "Contracten"
        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis NA"

        And New move customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
#            | Verbindingsstraat | 63     |            |     | 9220       | HAMME   |         |
            | Heistraat  | 83     |            |     | 2440       | GEEL   |         |

        And "Startdatum verhuis" date is "1 day before now"

        And "Is de meter geopend" turn on
        And "EAN-code" input is "parameter:randomEAN"
#        And Select EAN
        And "Datum meteropname" date is "1 day before now"
        And "Meterstand enkelvoudig" input is "1000"
        And Option "test" is on
#        And Option "MM should respond" is on
        And "MM should respond" turn on
        And Bevestigen


        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
#        And Get Contract Ean Code
        And "2nd" list element has cell value "Actief" at column "Contractnummer" polling 100 seconds
        And Table "Contracten" contains cell value "Sales Getekend (Geaccepteerd)" at column "Type & status" on "2nd" row
    	And Check if start date of new ean is the same date as filled in as “Move date”-"1 day before now"
    	And Check if the end date of new ean is the same date as the end date of the old one
        And Check if products of both contracts are the same
        And Check if discounts of both contracts are the same
        And Check if prices of both contracts are the same

##        And Top arrow button is "back"
        When Dashboard menu is "Marktberichten"
        Then Marketbericht with EAN "parameter:randomEAN" and module "START ACCESS" is in status "Gesloten"
        And Marketbericht with EAN "parameter:randomEAN" has ED "1 day before now"

        When Dashboard menu is "Service"
        Then There is a case where onderwerp is "VERHUIS"
        And  Interaction is created with Type "Document" and Onderwerp "Outbound document: Old inhabitant remains customer" and verwante case is "parameter:caseNumber"
