@ODOO
@CODA
Feature: Create a coda file

    Background:
        Given I logged in to Odoo as p.paulussen

    Scenario: Create a B2C Quote with customer switch
        When Top Action is Plus Menu
        And Plus Menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form Header is "Details van de offerte"

        When "Tariefdatum" date is "$today - 3 months"
        And B2C sales channel is Inbound
        And Quote details are confirmed
        Then Form Header is "Persoonsgegevens"

        When Customer is random
        And Customer Address is
