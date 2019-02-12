@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-482: Restart SA or MI - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    Scenario: Restart for SA Or MI
        When Left menu is "sales-marketing"
        And Top menu item is "Marktberichten"
        And Top action is "Filters"
        And "Label" selection is "Move In"
        And "Status EC" selection is "Geweigerd"
        Then Plus action and "Herstuur marktbericht" of first customer from list

        When "Startdatum" date is "3 weeks from now"
        And "Module" selection is "START ACCESS"
        And "Label" input is "Supplier Switch"
        And "Label" selection is "Supplier Switch"
        And "Testing" turn on
        And Save EAN code of customer
        Then Changes are confirmed

        When Left menu is "sales-marketing"
        And Search field input is "parameter:eanCode"
        And Click on link in View List at "1st" row and "Klantnaam & nummer" column polling 20 seconds
        And Dashboard menu is "Marktberichten"
        Then Validate rejection status is "START ACCESS"



