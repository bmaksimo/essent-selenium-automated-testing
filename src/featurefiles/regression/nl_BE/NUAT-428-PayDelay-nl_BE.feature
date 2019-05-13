@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
@NUAT-428
@UNSTABLE
Feature: NUAT-428: Pay Delay - nl_BE

    #First scenario is for preparing test data. And We split this part
    #because in preparing section we use different user credentials, credentials for billing user.
    #In Pay Delay scenario we use user who can not create invoice but this user can execute real scenario for dwp

    @NUAT-428-01
    Scenario: Preparing data for pay delay scenario
        Given I logged in to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Search field input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnaam & nummer" column polling 20 seconds
        And Plus menu is "Billing -> Vrije tekstfactuur aanmaken"
        And Click select product code
        And Select "Value_Samsung" product code
        And Send
        And Input in "Periode order" is "Recurrent maandelijks order"
        And "Value_Samsung PRODUCT_CODE_QUANTITY" input is "1"
        And "Value_Samsung PRODUCT_CODE_PRICE" input is "5718"
        Then Changes are confirmed

    @NUAT-428-02
    Scenario: Pay delay
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"
#        When Search field input is "1000103658"
        When Search field input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnaam & nummer" column polling 20 seconds

        When Dashboard menu is "Billing"
        Then "1st" list element has cell value "Invoice (FREE_TEXT)" at column "ID & Type" polling 450 seconds
        When List option is "ENKEL FACTUREN"
        Then Table "Openstaande facturen" contains value "Issued" at column "Extra info" within 600 seconds after clicking on "ENKEL FACTUREN"
        Given List element with value at column "Datum & Vervaldatum" from table "Openstaande facturen" is checked
        And Plus action of "1" element from "InvoicesOnAccountOpenBalance" and click on "Betalingsuitstel"
        And "Selecteer nieuwe vervaldatum" date is "3 week from now"
        When Changes are confirmed
        Then Payment delayed
