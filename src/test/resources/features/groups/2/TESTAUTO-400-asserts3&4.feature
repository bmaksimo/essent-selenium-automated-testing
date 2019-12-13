@DWP
@REGRESSION
@B2C
@ALL

Feature: NSTA-327: Creating a B2C Quote TC1 with "Supplier Switch" without using Market Mock

    @TESTAUTO-400
    Scenario: TESTAUTO-400 asserts

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "1000299834"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Marktberichten"
        And Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Search by "541441206246379400"
        And Changes are confirmed
        And "Module" selection is "INITIATE LEAVING CUSTOMER"
        And "Label" selection is "Without Handover Document "
        And Option "Testing?" is "On"
        And Changes are confirmed
        Then Table "Marktberichten" contains value "Geaccepteerd" at column "Status" retrying 10 times

        When Dashboard menu is "Service"
        And Table "Taken" contains value "check rest value" at column "Naam & Type & Subtype" retrying 5 times
        And Click on link in View List at "4th" row and "Number & Start date" column polling 60 seconds
        Then Positive amount is verified

