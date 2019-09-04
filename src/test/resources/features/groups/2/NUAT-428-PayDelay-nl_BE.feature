@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@PERFORMANCE
@ALL
Feature: NUAT-428: Pay Delay - nl_BE

    @NUAT-428
    Scenario: NUAT-428: Pay delay
        Given I logged in to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Plus menu is "Billing -> Vrije tekstfactuur aanmaken"
        And Click select product code
        And Select "Value_Samsung" product code
        And Send
        And Input in "Periode order" is "Recurrent maandelijks order"
        And "Value_Samsung PRODUCT_CODE_QUANTITY" input is "1"
        And "Value_Samsung PRODUCT_CODE_PRICE" input is "5718"
        Then Changes are confirmed

        Given I renew login to DWP as "businessdesk.testautomation.b2b@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds

        When Dashboard menu is "Billing"
        Then "1st" list element has cell value "Invoice (FREE_TEXT)" at column "ID & Type" polling 450 seconds
        And "1st" list element has cell value "Issued" at column "Extra info" polling 3600 seconds
        When List option is "ENKEL FACTUREN"
        Given List element with value at column "Datum & Vervaldatum" from table "Openstaande facturen" is checked
        And Plus action of "1" element from "InvoicesOnAccountOpenBalance" and click on "Betalingsuitstel"
        And "Selecteer nieuwe vervaldatum" date is "3 weeks from now"
        When Changes are confirmed
        Then Payment delayed
