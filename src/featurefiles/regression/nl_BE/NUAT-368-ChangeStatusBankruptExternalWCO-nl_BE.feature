@DWP
#@B2B
#@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-368: Change Status Bankrupt External WCO - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: I Change status CSR and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "csr"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "CSR"

    Scenario: II Change status CSR and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "csr"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "CSR"

    Scenario: III Change status Faillissement met fiscaal attest and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Faillissement met fiscaal attest"
        And Client signature file is uploaded
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Failliet"

        When Dashboard menu is Documenten
        Then Find document

    Scenario: IV Change status Faillissement met fiscaal attest and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Faillissement met fiscaal attest"
        And Client signature file is uploaded
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Failliet"

        When Dashboard menu is Documenten
        Then Find document

    Scenario: V Change status Faillissement zonder fiscaal attest and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Faillissement zonder fiscaal attest"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Failliet"

    Scenario: VI Change status Faillissement zonder fiscaal attest and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Faillissement zonder fiscaal attest"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Failliet"

    Scenario: VII Change status In WCO and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "In WCO"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "In WCO"

    Scenario: VIII Change status In WCO and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "In WCO"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "In WCO"

    Scenario: IX Change status Normaal and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Normaal"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Normaal"

    Scenario: X Change status Normaal and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Normaal"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Normaal"

    Scenario: XI Change status OCMW assistance and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "OCMW assistance"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "OCMW assistance"

    Scenario: XII Change status OCMW assistance and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "OCMW assistance"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "OCMW assistance"

    Scenario: XIII Change status OCMW budget management and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "OCMW budget management"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "OCMW budget management"

    Scenario: XIV Change status OCMW budget management and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "OCMW budget management"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "OCMW budget management"

    Scenario: XV Change status overleden and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "overleden"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Overleden!"

    Scenario: XVI Change status overleden and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "overleden"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Overleden!"

    Scenario: XVII Change status Provisional administration and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Provisional administration"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "Voorlopige bewindvoering"

    Scenario: XVIII Change status Provisional administration and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Provisional administration"
        And "Startdatum" date is "now"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "Voorlopige bewindvoering"

    Scenario: XIX Change status Vereffening and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Vereffening"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Contentia"
        And Changes are confirmed
        Then Verify status is "External @ Contentia" and "In vereffening"

    Scenario: XX Change status Vereffening and Externe partij is Hilde Derde
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filters
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Plus menu is "Service -> Wijzigingen klant -> Accountstatus wijzigen"
        And Update account status on "Vereffening"
        And "Extern" turn on
        And "Externe startdatum" date is "now"
        And Input in Externe partij is "Hilde Derde"
        And Changes are confirmed
        Then Verify status is "External @ Hilde Derde" and "In vereffening"
