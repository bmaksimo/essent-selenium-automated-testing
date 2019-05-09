@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
@UNSTABLE
@ALL

Feature: NUAT-446: Check The Usage Of A Customer - nl_BE

    Background:
        Given   I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-446
    Scenario: Checking usage of a customer
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds

        Given Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
        Then Consumption at deliverypointid "parameter:EAN-code" is generated until "1 year from now"
#And Click on "parameter:EAN-code" link
#And Consumption is available at "1st" row in "Van - Aan" column

        When Plus menu is "Billing -> Verbruiken voor klant"
        And View list header is "Verbruiken"
        Then "View" list is not empty
