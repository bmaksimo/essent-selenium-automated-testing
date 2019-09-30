@DWP
@B2B
@BUSINESS-DESK
@Ignore
Feature: NUAT-423: Change Bank Account - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-423
    Scenario: NUAT-423: Change Bank Account
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is "Details"
        And Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        And "Betalingswijze" selection is "Domiciliëring"
        And "IBAN" input is "BE71096123456769"
        And Changes are confirmed
        Then Validate bank account was changed on "parameter:inputValue"

        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Advanced search is
            |     field      |   operator  |          value          |
            | Account Number | is equal to | parameter:accountNumber |
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Odoo click on tab "Accounting"
        Then Odoo validate bank account was changed on "parameter:inputValue"

