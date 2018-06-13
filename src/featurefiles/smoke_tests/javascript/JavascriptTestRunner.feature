@SMOKE
Feature: Javascript using in DWP testing

    Background:
        Given I logged in in DWP as essentadmin
        Given I optionally discard a previous flow:

    Scenario: We can access the main filter elements
        Given Left Menu Item is Sales Marketing
        Then I smoke test all Javascript functions

        ##Then I see the filter input on the right part of main page
