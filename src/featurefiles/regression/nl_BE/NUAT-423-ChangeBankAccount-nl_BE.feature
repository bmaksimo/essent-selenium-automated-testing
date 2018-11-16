@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-423: Change Bank Account - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: I Change status CSR and Externe partij is Contentia
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And "Klantnummer" input is "1000000338"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column

        When Dashboard menu is Details
        And Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And "Betalingswijze" selection is "Domiciliëring"
        And "IBAN" input is "BE71096123456769"
        And Changes are confirmed
        Then Validate bank account was changed on "BE71096123456769"
