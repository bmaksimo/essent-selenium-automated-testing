@DWP
@B2B2
@REGRESSION2
@CREDIT-AND-CONTROL
@NUAT-368
Feature: NUAT-368: Change Status Bankrupt External WCO - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"

    @NUAT-368-01
    Scenario: I Change status CSR and Externe partij is Contentia
        When Update account status on "csr"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "CSR"

    @NUAT-368-02
    Scenario: II Change status CSR and Externe partij is Hilde Derde
        And Update account status on "csr"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "CSR"

    @NUAT-368-03
    Scenario: III Change status Faillissement met fiscaal attest and Externe partij is Contentia
        When Update account status on "Faillissement met fiscaal attest"
        And Client signature file is uploaded
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Failliet"
        When Dashboard menu is "Documenten"
        Then Find document

    @NUAT-368-04
    Scenario: IV Change status Faillissement met fiscaal attest and Externe partij is Hilde Derde
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

    @NUAT-368-05
    Scenario: V Change status Faillissement zonder fiscaal attest and Externe partij is Contentia
        When Update account status on "Faillissement zonder fiscaal attest"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Failliet"

    @NUAT-368-06
    Scenario: VI Change status Faillissement zonder fiscaal attest and Externe partij is Hilde Derde
        When Update account status on "Faillissement zonder fiscaal attest"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Failliet"

    @NUAT-368-07
    Scenario: VII Change status In WCO and Externe partij is Contentia
        When Update account status on "In WCO"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "In WCO"

    @NUAT-368-08
    Scenario: VIII Change status In WCO and Externe partij is Hilde Derde
        When Update account status on "In WCO"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "In WCO"

    @NUAT-368-09
    Scenario: IX Change status Normaal and Externe partij is Contentia
        When Update account status on "Normaal"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Normaal"

    @NUAT-368-10
    Scenario: X Change status Normaal and Externe partij is Hilde Derde
        When Update account status on "Normaal"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Normaal"

    @NUAT-368-11
    Scenario: XI Change status OCMW assistance and Externe partij is Contentia
        When Update account status on "OCMW assistance"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "OCMW assistance"

    @NUAT-368-12
    Scenario: XII Change status OCMW assistance and Externe partij is Hilde Derde
        When Update account status on "OCMW assistance"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "OCMW assistance"

    @NUAT-368-13
    Scenario: XIII Change status OCMW budget management and Externe partij is Contentia
        When Update account status on "OCMW budget management"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "OCMW budget management"

    @NUAT-368-14
    Scenario: XIV Change status OCMW budget management and Externe partij is Hilde Derde
        When Update account status on "OCMW budget management"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "OCMW budget management"

    @NUAT-368-15
    Scenario: XV Change status overleden and Externe partij is Contentia
        When Update account status on "overleden"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Overleden!"

    @NUAT-368-16
    Scenario: XVI Change status overleden and Externe partij is Hilde Derde
        When Update account status on "overleden"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Overleden!"

    @NUAT-368-17
    Scenario: XVII Change status Provisional administration and Externe partij is Contentia
        When Update account status on "Provisional administration"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Voorlopige bewindvoering"

    @NUAT-368-18
    Scenario: XVIII Change status Provisional administration and Externe partij is Hilde Derde
        When Update account status on "Provisional administration"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Voorlopige bewindvoering"

    @NUAT-368-19
    Scenario: XIX Change status Vereffening and Externe partij is Contentia
        When Update account status on "Vereffening"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "In vereffening"

    @NUAT-368-20
    Scenario: XX Change status Vereffening and Externe partij is Hilde Derde
        When Update account status on "Vereffening"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in "Externe partij" is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "In vereffening"


