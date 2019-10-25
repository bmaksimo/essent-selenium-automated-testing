@DWP
@B2B
@REGRESSION
@ALL
@Unstable

Feature: NSTA-351: Create free text invoice - nl_BE

    Background:
        Given   I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    @NSTA-351
    Scenario: NSTA-351: Create free text invoice
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 120 seconds
        And Click on top menu button PLUS and navigate to "Billing -> Vrije tekstfactuur aanmaken"

        And Click select product code
        And Select "Value_Samsung" product code
        And Input in "Periode order" is "Recurrent maandelijks order"
        And Send

        And "Value_Samsung PRODUCT_CODE_QUANTITY" input is "1"
        And "Value_Samsung PRODUCT_CODE_PRICE" input is "5718"
        And Sleep for 10 seconds
        Then Changes are confirmed
        And Sleep for 5 seconds

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (FREE_TEXT)" at column "ID & Type" retrying 10 times
