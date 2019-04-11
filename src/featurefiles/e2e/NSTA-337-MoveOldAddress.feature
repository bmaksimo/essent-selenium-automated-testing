@REGRESSION
@DWP
@B2C
@REGRESSION

Feature: NSTA - 337 Move old address

#    Background:
#
#        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-337
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
#        And Kortingen is "50_part"
#        And Package and Fuel Type is confirmed
#        Then Form header is "Connection details"
#
#        When EAN code is generated
#        And "Startdatum" date is "5 day before now"
#        And "EAN-code" input is "parameter:EAN-code-generated"
#        And "Meternummer" input is "1000"
#        And Option "test" is On
#        And Connection details are confirmed
#        Then Form header is "Billing details"
#
#        When "Betalingswijze" selection is "Overschrijving"
#        And  Billing details are confirmed
#        Then  Form header is "Quote overview"
#
#        When Option "Heeft de klant al getekend?" is On
#        And "Kanaal ondertekening" selection is "Papier"
#        And "Datum ondertekening" date is "now"
#        And Quote is signed
#        And Quote is signed in "Kontich"
#        When Quote is confirmed
#
#        When Dashboard menu is "Marktberichten"
#        Then View List is empty
#
#        When Dashboard menu is "Contracten"
#        Then View list header is "Actieve en toekomstige connecties"
#        And  "1st" List element with value at column "EAN-code" is checked
#        And Get Account Number
#        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        When I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        And Left menu is "contracting-switching"
#        And Click on hamburger menu
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2C"
        And "Type klant" selection is "Klant"
        And "Klantnummer" input is "1000100906"

#        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Contracten"
        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis OA"

        And Options "Testing?" is On
        And Options "Market mock?" is On
        And Communication channel is "E-mail"
        And Reason of move "Normal move"
        And Move date is "now"

        And Options "Is de nieuwe bewoner de eigenaar?" is On
        And "De nieuwe bewoner is" selection is "Particulier"
        And "Aanspreking" selection is "Meneer"
        And "Voornaam" input is "Petar"
        And "Familienaam" input is "Perovic"
        And "Taal" selection is "Nederlands"

        And "Gsm-nummer" input is "+32 483 08 06 44"
        And "E-mailadres" input is "petar.perovic@test.com"

        And "Datum meteropname" date is "now"
        And Get meter reading plus "1000"kwl from "1"
        And "Meterstand" input is "parameter:meterstand"

        And Datum meteropname low date is "now"
        And Get meter reading plus "1000"kwl from "2"
        And Meterstand low input is "parameter:meterstand"

        And Options "Push through incomplete move?" is On

        Then Changes are confirmed

#        When Dashboard menu is "Details"
        And Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And Change house number by "1"
        Then Changes are confirmed

        When Dashboard menu is "Service"
