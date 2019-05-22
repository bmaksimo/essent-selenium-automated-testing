@REGRESSION
@DWP
@B2C
@ALL
Feature: NSTA - 337 Move old address

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-337
    Scenario: Move old address
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

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When EAN code is generated
        And "Startdatum" date is "5 day before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Meternummer" input is "1000"
        And Option "test" is On
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Quote is signed
        And Quote is signed in "Kontich"
        When Quote is confirmed

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And  "1st" List element with value at column "EAN-code" is checked
        And Get Account Number
        And Copy product name
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        Given I renew login to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "B2C/B2B" selection is "B2C"
        And "Type klant" selection is "Klant"

        And "Klantnummer" input is "parameter:accountNumber"
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

        And Get meter reading plus "1000"kwl from "2"
        And Low meter reading input is "parameter:meterstand"
        And Low date meter reading date is "now"

        And Options "Push through incomplete move?" is On

        Then Bevestigen

        When Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And Change house number to "4"
        Then Changes are confirmed

        When Dashboard menu is "Service"
        Then There is a case where onderwerp is "Verhuis"
        And Interaction is created with Type "Interaction" and Onderwerp "Move OA"

        When View list header is "Cases" appears within 20 seconds
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        And  Go to prospect
        Then Check customer information
            |              address            |      phone       |         email         |
            | Mechelsesteenweg 2 2550 Kontich | +32 483 08 06 44 | petar.perovic@test.com|

        When Dashboard menu is "Service"
        Then Interaction is created with Type "Document" and Onderwerp "Outbound document: Move - New Inhabitants"

        When Top arrow button is "up"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Service"
        And View list header is "Cases" appears within 20 seconds
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        And Go to GLN account
        Then Check if customer name contains "GLN"
        And Check customer information
            |              address            | phone | email |
            | Mechelsesteenweg 2 2550 Kontich |       |       |

        When Dashboard menu is "Marktberichten"
        Then Marktbericht has label "CUSTOMER SWITCH"
        And Check marktbericht
            |               ean            |     modul    |  end date |
            | parameter:EAN-code-generated | START ACCESS |     now   |

        When Dashboard menu is "Contracten"
        Then Check contract
            | type |         status          | start date |              EAN             |      product      |
            | GLN  | Verwerkt (Geaccepteerd) |    now     | parameter:EAN-code-generated | parameter:product |

