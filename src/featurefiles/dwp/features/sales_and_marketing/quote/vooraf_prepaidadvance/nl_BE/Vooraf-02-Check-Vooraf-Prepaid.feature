@DWP
@B2C
Feature: NUAT-5021 Voraaf Step 2. Check vooraf (prepaid)

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-391
    @VOORAF-CHECKS
    Scenario: Sign-in on Vooraf (prepaid)

        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        Then View list header is "Klanten" appears within 25 seconds

        When Top action is "Filters"
        And  "Klantnummer" input is "1000120974"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is "Service"
        Then List element matching value "Outbound document: prepaidadvance" at column "Type & Onderwerp" from table "Interacties" is checked

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (PREPAIDADVANCE)" at column "ID & Type"
        And  "1st" element of table "Transacties" at currency column "Bedrag" is sum of
            |parameter:bedrag-vooraf-el |
            |parameter:bedrag-vooraf-gas|





