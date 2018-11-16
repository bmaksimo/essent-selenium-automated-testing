@DWP
@REGRESSION
@B2B
@CREDIT-AND-CONTROL
Feature: NUAT-372: Duplicate Customer - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Duplicate customer
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "Naam" input is "%Steven%"
        And "Klantnummer" input is "150638828"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Plus menu is "Service -> Dupliceer klant"
        When "Bedrijfsnaam" input is "Van Hauwaert Steven - Test Nuat 372"
        Then Changes are confirmed
        And Top arrow button is Up
        And Search input is parameter:inputValue
        Then Customer "parameter:inputValue" is found

