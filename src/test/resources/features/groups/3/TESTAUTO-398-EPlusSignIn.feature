@REGRESSION
@DWP
@B2C
@REGRESSION
@ALL


Feature: TESTAUTO-398 E plus sign in

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-398
    Scenario: E plus sign in
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

