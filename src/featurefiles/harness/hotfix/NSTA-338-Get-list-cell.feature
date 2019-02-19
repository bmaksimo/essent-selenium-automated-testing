@DWP
Feature: View list model  function, getting cell value for given table

    Background:
        Given   I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @NSTA-388-HARNESS
    Scenario: Check the table values at given rows
        Given Parameter "suitecrm-customer-nr" is "1000079327"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:suitecrm-customer-nr"
        And View list header is "Klanten"
        And Click on "parameter:suitecrm-customer-nr" link
        And Dashboard menu is "Contracten"
        And View list header is "Actieve en toekomstige connecties"
        Then Table "Contracten" contains cell value "Sales Getekend (Geaccepteerd)" at column "Type & status" on "2nd" row
