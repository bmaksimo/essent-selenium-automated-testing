@DWP
@B2C
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
#        When Package is "Vast"
#        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
#        And Package and Fuel Type is confirmed
#        Then Form header is "Connection details"
#
#        When Electricity EAN code is "random"
#        And Connection details are confirmed
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
#
#        When Top arrow button is "Up"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "1000071209"
#        And "Naam" input is "parameter:suitecrm-customer-name"

        When Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is "Contracten"
#        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis NA"
        And plus jtim
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Verbindingsstraat | 63     |            |     | 9220       | HAMME   |         |

        And "Startdatum verhuis" date is "1 day before now"


        And "Is de meter geopend" turn on
        And "Test" is turned on
        And "MM should respond" turn on
        And "Meterstand enkelvoudig" input is "1000"
        And "Datum meteropname" date is "1 day before now"
        And Select EAN
        And Changes are confirmed

