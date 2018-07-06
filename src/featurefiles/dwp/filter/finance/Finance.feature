@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP Filter Options

    Background:
        Given I logged in in DWP as Finance
        Given I optionally discard a previous flow
    Scenario: Checking whether filter elements, defined for "Finance" Left Menu, are available
        When Left Menu Item is finance
        And  Top Menu Item is Accounts
        Then Available filter elements are:
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


