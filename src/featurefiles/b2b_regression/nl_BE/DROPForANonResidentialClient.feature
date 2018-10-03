@B2B_REGRESSION
Feature: DROP for a Non-residential client

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: DROP for a Non-residential client
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds


        When Top action is Filters
