@DWP
@B2B
@BUSINESS-DESK
Feature: NAUT-490: Handle Task For Cancel - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Handle task for canceling
        When Left menu is Werkbakken
        And Top action is Filters
        Then "Status" selection is "Open"

        When Save task ID of first customer in list
        And Plus action and "Mark as done" of first customer from list
        And Resolution input is "Mark as done for testing"
        And Changes are confirmed
        And Task was marked as done
        Then View List is empty
