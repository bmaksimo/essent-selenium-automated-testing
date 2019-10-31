@REGRESSION
@DWP
@B2C
@ALL
@Unstable

Feature: NSTA-337: Move old address - Electricity

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-337
    Scenario: NSTA-337: Move old address - Electricity
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"
        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"
        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Contracten"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis OA"
        
        And Option "Testing?" is "On"
        And Option "Market mock?" is "On"
        And Communication channel is "E-mail"
        And Reason of move "Normal move"
        And Move date is "now"

        And Option "Is de nieuwe bewoner de eigenaar?" is "On"
        And "De nieuwe bewoner is" selection is "Particulier"
        And "Aanspreking" selection is "Meneer"
        And "Voornaam" input is "Petar"
        And "Familienaam" input is "Perovic"
        And "Taal" selection is "Nederlands"

        And "Gsm-nummer" input is "+32 483 08 06 44"
        And "E-mailadres" input is "petar.perovic@test.com"

        And "Datum meteropname" date is "now"
        And Get meter reading plus "1000" kwl from "1"
        And "Meterstand" input is "parameter:meterstand"

        And Get meter reading plus "1000" kwl from "2"
        And Low meter reading input is "parameter:meterstand"
        And Low date meter reading date is "now"

        And Option "Push through incomplete move?" is "On"
        And Sleep for 20 seconds
        Then Bevestigen

        When Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And Change house number to "4"
        Then Changes are confirmed

        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Service"
        Then Table "Cases" contains value "Verhuis" at column "Onderwerp" retrying 15 times
        And Interaction is created with Type "Interaction" and Onderwerp "Move OA"

        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        Then Interaction is created with Type "Document" and Onderwerp "Outbound document: Move - New Inhabitants"
        And  Go to prospect
        And Sleep for 30 seconds
        Then Check customer information
            | address                         | phone            | email                  |
            | Mechelsesteenweg 2 2550 Kontich | +32 483 08 06 44 | petar.perovic@test.com |

        When Click on top menu button UP
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Service"
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        And Go to GLN account
        Then Check if customer name contains "GLN"
        And Check customer information
            | address                         | phone | email |
            | Mechelsesteenweg 2 2550 Kontich |       |       |

        When Dashboard menu is "Marktberichten"
        And Market message contains:
            | EAN-code & Producttype | Module & Label | Module & Label  | Status & ED |
            | parameter:EAN-code     | START ACCESS   | CUSTOMER SWITCH | now         |

        When Dashboard menu is "Contracten"
        Then Check contract
            | type | status                  | start date | EAN                | product           |
            | GLN  | Verwerkt (Geaccepteerd) | now        | parameter:EAN-code | parameter:product |
