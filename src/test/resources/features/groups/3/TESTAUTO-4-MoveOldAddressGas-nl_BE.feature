@REGRESSION
@DWP
@B2C
@API
@ALL
@Unstable

Feature: TESTAUTO - 4 Move old address  - Gas ean

    Background:
        Given I login as API user "soapui_b2c"

    @TESTAUTO-4
    Scenario: Move old address - Gas
        #Contract creation via API
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
        And  "1st" List element with value at column "EAN-code" is checked
        And Copy product name
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        When Dashboard menu is "Contracten"
        And Plus action of "1" element from "ContractsOnAccount" and click on "Verhuis OA"

        And Options "Testing?" "is" "On"
        And Options "Market mock?" "is" "On"
        And Communication channel is "E-mail"
        And Reason of move "Normal move"
        And Move date is "now"
        And Options "Is de nieuwe bewoner de eigenaar?" "is" "On"
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

        And Options "Push through incomplete move?" "is" "On"
        Then Bevestigen

        When Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And Change house number to "4"
        Then Changes are confirmed
        And Sleep for 10 seconds

        When Dashboard menu is "Service"
        Then There is a case where onderwerp is "Verhuis"
        And Interaction is created with Type "Interaction" and Onderwerp "Move OA"

        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 60 seconds
        And  Go to prospect
        Then Check customer information
            | address                         | phone            | email                  |
            | Mechelsesteenweg 2 2550 Kontich | +32 483 08 06 44 | petar.perovic@test.com |

        When Dashboard menu is "Service"
        Then Interaction is created with Type "Document" and Onderwerp "Outbound document: Move - New Inhabitants"

        When Top arrow button is "up"
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Service"
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 60 seconds
        And Go to GLN account
        Then Check if customer name contains "GLN"
        And Check customer information
            | address                         | phone | email |
            | Mechelsesteenweg 2 2550 Kontich |       |       |

        When Dashboard menu is "Marktberichten"
        Then Market message contains:
            | EAN-code & Producttype | Module & Label | Module & Label  | Status & ED |
            | parameter:EAN-code     | START ACCESS   | CUSTOMER SWITCH | now         |

        When Dashboard menu is "Contracten"
        Then Check contract
            | type | status                  | start date | EAN                |
            | GLN  | Verwerkt (Geaccepteerd) | now        | parameter:EAN-code |
