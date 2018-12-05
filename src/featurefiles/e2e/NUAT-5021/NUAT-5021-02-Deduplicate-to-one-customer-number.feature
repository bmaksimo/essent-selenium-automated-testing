@DWP
@E2E
@B2C
@DEDUPLICATE
Feature: NUAT-5021 Step 2. Deduplicate to 1 customer number

    Background:

        Given I logged in to DWP as salesmarketing.testautomation.b2c@essent.be

    @NUAT-5021-STEP-2
    Scenario: Trigger Invoice run process
        When Left menu is sales-marketing
        And Top menu item is Contracten
        Then View list header is "Contracten"

        And Top action is Filters
        And "Pricing group" selection is "TK1"
        And "Status contractlijn" selection is "Actief"


