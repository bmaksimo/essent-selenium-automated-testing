@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-423: Change Bank Account - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-423
    Scenario: Change status CSR and Externe partij is Contentia
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And "1st" List element with value at column "Id Billing customer & persoon/familie sleutel" is checked
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
        And Advanced search is "Account Number", "is equal to", "parameter:accountNumber"
#        And Advanced search is "Account Number", "is equal to", "1000099729"
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Odoo click on tab "Accounting"
        Then Odoo validate bank account was changed on "parameter:inputValue"
#        Then Odoo validate bank account was changed on "BE71096123456769"
