@DWP
@BASIC
@_MENU
@REGRESSION
Feature: DWP UI: Menu structure, specific menu items

    Background:
        Given I logged in to DWP as d.chebayewski.billinghouse@essent.be

    Scenario: We can click the left menu items and access the corresponding top menus
        Then Available Left Menu items are:
            | Sales Marketing       |
            | Contracting Switching |

