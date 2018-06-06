@SMOKE
Feature: Javascript using in DWP testing

    Background:
        Given I logged in as ESSENT_ADMIN on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We can access the main filter elements
        When Left Menu Item 'SALES_MARKETING' is made active
        Then I smoke test all Javascript functions

        ##Then I see the filter input on the right part of main page
