@DWP
@E2E
@B2C
@DEDUPLICATE
Feature: NUAT-5021 Step 2. Deduplicate to 1 customer number

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    @NUAT-5021-STEP-2
    Scenario: Trigger Invoice run process
#        When Left menu is sales-marketing
#        And Top menu item is Contracten
#        Then View list header is "Contracten"
#
#        And Top action is Filters
#        And "Pricing group" selection is "TK1"
#        And "Status contractlijn" selection is "Actief"

        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form header is "Personal details"

        And "Aanspreking" selection is "Meneer"
        And "Voornaam" input is "parameter:customer-first-name"
        And "Familienaam" input is "parameter.customer-last-name"
        And "E-mailadres" input is "parameter.customer-email"
        And "Geboortedatum" input is "parameter.customer-birth-name"
        And "Gsm-nummer" input is "parameter.customer-gsm-number"

        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |


