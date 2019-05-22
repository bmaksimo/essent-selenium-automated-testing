@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-553: Create Lead And Convert Lead - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-553
    Scenario: Create lead and convert lead
        When Left menu is "sales-marketing"
        Then Top menu item is "Leads"

        When Add lead
        And New lead is
            | companyName    | firstName | secondName |
            | ESSENT BELGIUM | Levi      | Nine       |
        And "Geslacht" selection is "Onbekend"
        And "Telefoon" input is "+32 78 15 79 79"
        And "Mobiel" input is "+32 498 12 34 56"
        And "E-mailadres" input is "test@test.be"
        And Options "Bel me niet?" is On
        Then Save changes

        When Plus action and "Converteer lead" of first customer from list
        Then Changes are confirmed

        When Dashboard menu is "Details"
        And Klanttype is "Prospect"
        Then There is one billing customer
