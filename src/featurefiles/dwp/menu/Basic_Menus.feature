@DWP
@BASIC
@_MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in as admin on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We can click the left menu items and access the corresponding top menus
        When The following Left Menu items are available:
            | SALES_MARKETING       |
            | CONTRACTING_SWITCHING |
            | BILLING               |
            | CREDIT_MANAGEMENT     |
            | FINANCE               |
            | SERVICE               |
            | ESS                   |
            | ADMIN                 |
