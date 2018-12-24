@DWP
@B2C
Feature: NUAT-5021 Step 2. Deduplicate account

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    @CREATE-QUOTE-FOR-ACCOUNT
    Scenario: Trigger Deduplicate process


        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form header is "Personal details"
        And "Aanspreking" selection is "Meneer"
        And "Voornaam" input is "Luka"
        And "Familienaam" input is "Braun"
        And "E-mailadres" input is "luka.braun@example.com"
        And "Gsm-nummer" input is "+31686353147"
        And "Geboortedatum" date is "03/02/1973"

        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |

        And Customer details are confirmed
        Then Form header is "Select package & fuel type"


