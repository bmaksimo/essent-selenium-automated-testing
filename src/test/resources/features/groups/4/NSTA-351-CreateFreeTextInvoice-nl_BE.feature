@DWP
@B2B
@REGRESSION
@ALL

Feature: NSTA-351: Create free text invoice - nl_BE

    Background:
        Given   I logged in to DWP as "billing.testautomation@essent.be"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    @NSTA-351
    Scenario: Checking usage of a customer
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 120 seconds
        And Plus menu is "Billing -> Vrije tekstfactuur aanmaken"

        And Click select product code
        And Select "Value_Samsung" product code
        And Input in "Periode order" is "Recurrent maandelijks order"
        And Send

        And "Value_Samsung PRODUCT_CODE_QUANTITY" input is "1"
        And "Value_Samsung PRODUCT_CODE_PRICE" input is "5718"
        Then Changes are confirmed

        When Dashboard menu is "Billing"
        Then Transaction is created with TYPE "INVOICE (FREE_TEXT)"
