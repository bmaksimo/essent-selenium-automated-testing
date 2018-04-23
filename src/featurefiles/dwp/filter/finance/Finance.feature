@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP Filter Options

    Background:
        Given I logged in as admin on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We see all Finance Filter elements
        When I click on the following Left Menu item: 'FINANCE'
        Then I check filter elements defined for 'FINANCE' Left Menu Item and 'ACCOUNTS_LIST' Top Menu Item
        |B2C/B2B|
        |Category|
        |Customer Type|
        |Segment|
        |EAN|
        |Account number|
        |Name|
        |Contactperson|
        |Headquarter street|
        |Invoice street|
        |Connection street|
        |IBAN|
        |Company number|
        |Birthdate|
        |E-mail|
        |Billing customer nr|
        |Assigned dealer|
        |Assigned user|


