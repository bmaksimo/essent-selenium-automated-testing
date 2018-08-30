@DWP
@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as contracting.testautomation.b2c@essent.be

    Scenario:
        When Left menu is contracting-switching
        And Top Menu Item is Marktberichten
        Then View List Header is "Marktberichten" appears within 25 seconds

        When Top Action is Filters
        And "Aangemaakt na" date is "$today - 3months"
        And "Aangemaakt voor" date is "$today +  1 day"
        And "EAN-code" input is "541448820045086029"
        Then 1 List row having cell value MOVE IN Move In at column Module & Label is selected

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP-TK2 - Om contracten te hernieuwen"
        Then View List Header is "UP-TK2 - Om contracten te hernieuwen" appears within 10 seconds

        When Top Action is Filters
        And "Klantnummer" input is "6574"
        Then View List is empty



