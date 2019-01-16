@JBILLING
@REGRESSION
@B2B
Feature: NAUT-501: Check invoice date

    Background:
        Given I logged in to JBilling as "billing_testautomation"

    Scenario: Check invoice date
        When JBilling top menu item is "Configuration"
        And Configuration left menu item is "Billing Process"
        And Choose on time billing process
        And Select edit billing proces
        And Invoice date is "8" days ago
        And Save billing proces
        Then Error message is displayed

        When Cancel billing proces
        And Choose on time billing process
        And Select edit billing proces
        And Date in not changed
        Then Cancel billing proces

