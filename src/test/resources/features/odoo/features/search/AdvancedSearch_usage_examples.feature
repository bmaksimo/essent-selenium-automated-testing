@POC
@SPIKE
Feature: Different advanced search possibilities. Intended for showing possible usages, has no business value.

    @ADV-SEARCH-EXAMPLE1
    Scenario: Basic test advanced search
        Given I logged in to Odoo as "role_essent_ccm_user"
        When Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Advanced search is "Account Number", "is equal to", "150006466"
        When Column "Account Number" of the "1st" row is clicked
