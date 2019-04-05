@DWP
@CREDIT-AND-CONTROL
@B2C
Feature: NUAT-5019 Step 5. Billing - Triggering mediation run, including the received consumptions data into new order

    Background:

        Given I logged in to DWP as "billing.testautomation@essent.be"

    @MEDIATION-RUN
    @NUAT-5019-STEP-5
    Scenario: Trigger Mediation run process

        When Top arrow button is "Up"
        And Left menu is "billing"
        And Top menu item is "Klanten"
        And Plus menu is "Billing -> Start mediationrun"
        Then Modal dialog is "Start mediationrun"

        Given "Naam job" selection is "Voorschot"
        And "ID Billing customer" input is "parameter:Id Billing customer & persoon/familie sleutel"
        And "Datum afrekeningsfactuur" date is "parameter:billrun-date"
        Then Form is submitted
