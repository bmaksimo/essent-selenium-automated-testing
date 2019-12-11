@REGRESSION
@DWP
@B2C
@REGRESSION
@ALL

Feature: Testauto-398 asserts

    @TESTAUTO-398
    Scenario: Testauto-398 asserts
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Contracten"
        And Get Start Date
        Then Click on link in View List at "1st" row and "Contractnummer" column polling 60 seconds

        #Asserts
        And Table "Contractlijnen" contains value "E+ service" at column "Producttype" retrying 5 times
        And Table contains matching data on given columns:
           | Table name     | Status & Product      | Producttype   |
           | Contractlijnen | Actief - Wait to send | E Plus Product|
        And Number of contract lines is "4"
        Then Start dates are same for all contract lines as contract start date "parameter:startDate"

        When Dashboard menu is "Service"
        Then Table "Interacties" contains value "Document Outbound document: CONF_CONTRACT_SALES_TC1_B2C" at column "Type & Onderwerp" retrying 5 times
        And Table "Interacties" contains value "Document Outbound document: Activation Letter" at column "Type & Onderwerp" retrying 5 times

        When Click on link in View List at "1st" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        Then EMC ID is present

        When Dashboard menu is "Service"
        When Click on link in View List at "2nd" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        Then EMC ID is present
