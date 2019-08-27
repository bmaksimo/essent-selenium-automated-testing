@DWP
@CORE
@CORE-FILTER
Feature: Applying filters, View List Header check with waiter

    Background:
        Given   I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    Scenario: Menu navigation and filter input
        When Left menu is "contracting-switching"
        And Top menu item is "Marktberichten"

        When Top action is "Filters"
        And "Aangemaakt na" date is "3 months before now"
        And "Aangemaakt voor" date is "1 day from now"
        And "Label" selection is "Move In"
        Then "1" List row having cell value "MOVE IN Move In" at column "Module & Label" is selected

    Scenario: Plus Menu navigation to view list
        When Plus menu is "Contracting -> UP-TK2 - Om contracten te hernieuwen"

        When Top action is "Filters"
        And "Klantnummer" input is "6574"
        Then View List is empty



