@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-428: Pay Delay - nl_BE

    #First scenario is for preparing test data. And We split this part
    #because in preparing section we use different user credentials, credentials for billing user.
    #In Pay Delay scenario we use user who can not create invoice but this user can execute real scenario for dwp

    Scenario: Preparing data for pay delay scenario
        Given I logged in to DWP as billing.testautomation@essent.be
        When Left menu is billing
        And Top menu item is Klanten
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Search field input is "parameter:accountNumber"
        And Click on link in View List at 1st row and "Klantnaam & nummer" column polling 20 seconds
        And Plus menu is "Billing -> Vrije tekstfactuur aanmaken"
        And Input in Periode order is "Recurrent maandelijks order"
        And Input in Id item is "Value Samsung (5718)"
        And "Prijs" input is "5718"
        And "Kwantiteit" input is "1"
        Then Changes are confirmed

    Scenario: Pay delay
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be
        When Search field input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnaam & nummer" column polling 20 seconds

        When Dashboard menu is Billing
        And List option is "ENKEL FACTUREN"
        And Find "Issued" facture and "Betalingsuitstel"
        And "Selecteer nieuwe vervaldatum" date is "3 week from now"
        And Changes are confirmed
        Then Payment delayed
