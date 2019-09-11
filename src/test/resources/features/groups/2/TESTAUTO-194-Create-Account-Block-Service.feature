@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-193 Dunning-create-account-block

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
    @TESTAUTO-194
    Scenario: create-account-block
        When Left menu is "sales-marketing"
        And Top menu item is "Clients"
        When B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        And Dashboard menu is "Détails"
        And Click on "CRÉER UN BLOC"
        And "Block reason" selection is "E-plus"
        And "Date de fin" date is "7 days from now"
        And Sleep for 10 seconds
        And Changes are confirmed
        And Sleep for 60 seconds

        Then Table "Liste des blocs" contains value "E-plus" at column "Raison" retrying 60 times
        And Table "Liste des blocs" contains value "true" at column "Actif" retrying 60 times
        And Account block start and end dates are the same
