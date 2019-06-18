@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-424: Change Pay Method - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-424
    Scenario: Change payment method

        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Dashboard menu is "Details"
        Then View list header is "Billing customer"

        When Plus action of "1" element from "BillingCustomerOnaccount" and click on "Update"
        Then Modal "Update billing customer" is displayed

        When Payment method is switched
        And IBAN is "NL43ABNA0978459932" if not empty
        And Payment details are confirmed
        Then Payment method is updated
        When Dashboard menu is "Documenten"
        Then Check if document "customer-signature.pdf" is present

        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Advanced search is
            |     field      |   operator  |          value          |
            | Account Number | is equal to | parameter:accountNumber |
        When Column "Account Number" with value "parameter:accountNumber" is clicked
        And Odoo click on tab "Accounting"
        Then Odoo verify payment method has changed to "Wire transfer"
