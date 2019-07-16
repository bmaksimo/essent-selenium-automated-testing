@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-482: Restart SA or MI - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-482
    Scenario: Restart for SA Or MI
        When Left menu is "sales-marketing"
        And Top menu item is "Marktberichten"
        And Top action is "Filters" waiting for 30 seconds
        And "Label" selection is "Move In" waiting for 30 seconds
        And "Status EC" selection is "Geweigerd" waiting for 30 seconds
        Then Plus action and "Herstuur marktbericht" of first customer from list waiting for 30 seconds

        When "Startdatum" date is "3 weeks from now" waiting for 45 seconds
        And "Einddatum" date is "1 year from now" waiting for 45 seconds
        And "Module" selection is "START ACCESS" waiting for 10 seconds
        And "Label" selection is "Supplier Switch" waiting for 10 seconds
        And "Testing" turn on waiting for 10 seconds
        And Save EAN code of customer now
        Then Changes are confirmed waiting for 10 seconds

        When Left menu is "sales-marketing" waiting for 10 seconds
        And Search field input is "parameter:eanCode" waiting for 30 seconds
        And Click on link in View List at "1st" row and "Klantnaam & nummer" column waiting for 20 seconds
        And Dashboard menu is "Marktberichten" waiting for 10 seconds
        Then Validate rejection status is "START ACCESS"

