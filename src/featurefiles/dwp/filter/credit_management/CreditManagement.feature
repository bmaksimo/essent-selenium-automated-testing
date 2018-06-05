@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in as ESSENT_ADMIN on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We see all Credit Filter elements
        When I click on the following Left Menu item: 'CREDIT_MANAGEMENT'
        Then I check filter elements defined for 'CREDIT_MANAGEMENT' Left Menu Item and 'ACCOUNTS_LIST' Top Menu Item
            |EAN|
            |Account number|

        #And I check filter elements defined for 'CREDIT_MANAGEMENT' Left Menu Item and ...
