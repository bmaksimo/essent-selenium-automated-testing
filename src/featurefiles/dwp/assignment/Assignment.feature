@ASSIGNMENT

Feature: Working with assignments

    Scenario: I create a new assignment

        Given I logged in in DWP as essentadmin
        When Left Tab is Sales Marketing
        And Top Action is Plus Menu
        And Top Tab is Accounts
        And Select the 1st element and click on the link in the Account Number & Name column
        And Overview is Details
        And Top Action is Plus Menu
        And Plus Menu is Create new assignment
        And Category is Random
        And Assignment is Random
