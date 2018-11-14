@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-553: Create Lead And Convert Lead - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Create lead and convert lead
        When Left menu is sales-marketing
        Then Top menu item is Leads

        When Add lead
        And New lead is
            | companyName    | firstName | secondName | telephone       | mobile           | email        | gender |
            | ESSENT BELGIUM | Levi      | Nine       | +32 78 15 79 79 | +32 498 12 34 56 | test@test.be | Onbekend |
        And Plus action and "Converteer lead" of first customer from list
        Then Changes are confirmed
