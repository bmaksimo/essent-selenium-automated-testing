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


        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |

        And "Aanspreking" selection is "Mevr."
        And "Voornaam" input is "Heike"
        And "Familienaam" input is "Van riessen"
        And "E-mailadres" input is "heike.vanriessen@example.com"
        #And "Gsm-nummer" input is "+31686353147"
        And "Geboortedatum" date is "04/07/1960"

        And Customer details are confirmed
        Then Form header is "Select package & fuel type"


