@REGRESSION
@DWP
@B2C
@REGRESSION
@ALL
    
Feature: TESTAUTO-398 E plus sign in asserts

    Scenario: E plus sign in asserts
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
