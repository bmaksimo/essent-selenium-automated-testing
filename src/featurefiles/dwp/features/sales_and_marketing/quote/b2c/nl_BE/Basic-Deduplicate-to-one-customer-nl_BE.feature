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
        And "Voornaam" input is "Sharona"
        And "Familienaam" input is "Cok"
        And "E-mailadres" input is "sharona.cok@example.com"
        And "Gsm-nummer" input is "+31686353147"
        And "Geboortedatum" date is "20/06/1975"

        When  Deduplication dialogue "Soortgelijke klanten" is shown
        And   Deduplication dialogue link "Create quote for account" is clicked
        Then Form header is "Quote details"
