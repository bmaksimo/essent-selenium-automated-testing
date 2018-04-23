@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter Elements

    Background:
        Given I logged in as admin on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We see all Billing Main Menu Filter elements
        When I check filter elements defined for Left Menu item: 'BILLING'
            |Task number|
            |Task ID|

    Scenario: We see all Billing -> 'BILLING' Filter elements
        When I click on the following Left Menu item: 'BILLING'
        Then I check filter elements defined for 'BILLING' Left Menu Item and 'ACCOUNTS_LIST' Top Menu Item
            |EAN|
            |Account number|
        And  I check filter elements defined for 'BILLING' Left Menu Item and 'CONTRACT_LIST' Top Menu Item
            |Contract number|
            |Delivery address street|
        And  I check filter elements defined for 'BILLING' Left Menu Item and 'CASES' Top Menu Item
            |Case number|
            |Status|
            |Priority|
