@DWP
@DEV-CREDIT-AND-CONTROL
@DEV
Feature: Billing - Soft dunning process tasks check

    Background:
        Given I logged in to DWP as "billing.testautomation@essent.be"

    @DEV-DUNNING
    @DEV-DUNNING-TASKS
    Scenario: Check if tasks have been generated when reaching HB3 dunning level
        #Menu Navigation
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Naam" input is "parameter:suitecrm-customer-name"
        And  "1st" List element with value at column "Klantnummer & Naam" is checked
        Then Click on link in View List at "1st" row and "Klantnummer & Naam" column


        And Dashboard menu is "Service"

        And  Table "Taken" contains value "Soft-Dunning Call POST HB3 B2C HIGH" at column "Naam & Type & Subtype"
        And  Table "Taken" contains value "Soft-Dunning Call POST HB2 B2C HIGH" at column "Naam & Type & Subtype"
        And  Table "Taken" contains value "Soft-Dunning Call POST HB1 B2C HIGH" at column "Naam & Type & Subtype"

