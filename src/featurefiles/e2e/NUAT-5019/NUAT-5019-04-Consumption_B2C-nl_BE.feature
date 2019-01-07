@DWP
@CREDIT-AND-CONTROL
@B2C
@CONSUMPTIONS
Feature: NUAT-5019 Step 4. Import received consumption data

    Background:

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @CONSUMPTIONS
    @NUAT-5019-STEP-4
    Scenario: Generate consumption for given customer, and verify the result in DWP

        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column
        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked

        Given Click on link in "Actieve en toekomstige connecties" View List at "1st" row and "EAN-code" column
        Then View list header is "Verbruiken"
        And "Verbruiken" list is empty

        Given Top arrow button is "Back"
        When Consumption at deliverypointid "parameter:EAN-code" is generated from now until "2019-09-30"
        And Click on link in "Actieve en toekomstige connecties" View List at "1st" row and "EAN-code" column
        Then View list header is "Verbruiken"
        And Consumption is available at "1st" row in "Van - Aan" column


