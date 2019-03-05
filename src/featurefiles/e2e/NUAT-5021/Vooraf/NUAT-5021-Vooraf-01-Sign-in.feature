@DWP
@B2C
Feature: NUAT-5021 Voraaf Step 1. Sign-in on vooraf (prepaid)

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NUAT-5021-VOORAF-1
    Scenario: Sign-in on Vooraf (prepaid)
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vooraf"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "2 weeks before now"

#    “Startdatum”: Needs to be a date in the past
#    • “Test”: Checkbox ON
#    • “MM should respond”: Checkbox ON
#    • Marktbericht: Select “Volledig Marktbericht”
