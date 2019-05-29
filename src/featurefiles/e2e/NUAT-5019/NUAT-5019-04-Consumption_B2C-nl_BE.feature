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

        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked

        When Click on "parameter:accountNumber" link
        And Dashboard menu is "Billing"
        Then "Openstaand bedrag" in the first "Paid by OV" row of "Transacties" table is "equal to 0"

        Given Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated from now until "2019-09-30"
        #And Click on "parameter:EAN-code" link
        #And Consumption is available at "1st" row in "Van - Aan" column


