@DWP
@ALL
@CREDIT-AND-CONTROL
@REGRESSION
@NUAT-368-SPLIT
Feature: NUAT-368: Change Status Bankrupt External WCO - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"
        
    @NUAT-368-SPLIT-01
    Scenario: I Change status CSR and Externe partij is Contentia
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"

        When Update account status on "csr"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "CSR"

    @NUAT-368-SPLIT-02
    Scenario: IV Change status Faillissement met fiscaal attest and Externe partij is Hilde Derde
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"

        When Update account status on "Faillissement met fiscaal attest"
        And Client signature file is uploaded
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Failliet"
        When Dashboard menu is "Documenten"
        Then Find document
