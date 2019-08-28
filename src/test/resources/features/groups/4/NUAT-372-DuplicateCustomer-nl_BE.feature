@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-372: Duplicate Customer - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-372
    Scenario: Duplicate customer
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        And Plus menu is "Service -> Dupliceer klant"
        When "Bedrijfsnaam" input is "Test Nuat 372"
        Then Changes are confirmed
        And Top arrow button is "Up"
        And Search field input is "parameter:inputValue"
        Then Customer "parameter:inputValue" is found
