#@B2B_REGRESSION
Feature: Dwp test for Creating Lead - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        Then Top menu item is Leads

        When Add lead
        And Insert company name "Levi9" for creating lead
