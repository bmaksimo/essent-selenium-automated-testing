@B2B_REGRESSION
Feature: Dwp test for Creating Lead - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        Then Top menu item is Leads

        When Add lead
        Then New lead is
            | companyName    | firstName | secondName | telephone       | mobile           | email        |
            | ESSENT BELGIUM | Levi      | Nine       | +32 78 15 79 79 | +32 498 12 34 56 | test@test.be |
