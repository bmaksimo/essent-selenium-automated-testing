@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-193 Dunning-create-account-block

     Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @TESTAUTO-193
    Scenario: create-account-block
        When Left menu is "contracting-switching"
        And Sleep for 60 seconds
        And Top menu item is "Clients"
        When B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "contracting-switching" menu retrying 5 times
        And "Numéro de client" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contrats"
        And "1st" list element has cell value "ACTIVER" at column "Numéro de contrat" polling 500 seconds
        And Dashboard menu is "Détails"
