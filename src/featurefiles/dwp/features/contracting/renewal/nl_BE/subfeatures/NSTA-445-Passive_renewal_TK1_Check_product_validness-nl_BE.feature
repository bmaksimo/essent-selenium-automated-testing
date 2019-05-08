@DWP
Feature: NSTA-445 Passive renewal of contract TK1 - with communication through Invoice

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NSTA-445-4
    Scenario: Check validness period of product to renew
        #1. Onboarding of B2C customer, with TC1 quote and active contract.
        When Plus menu is "Contracting -> TK1 Hernieuwingen -> Bepaal het hernieuwingsproduct"
        Then View list header is "Bepaal het hernieuwingsproduct" appears within 20 seconds

        When Top action is "Filters"
        And  Selection with search is "Van pakket"
        And  Modal dialog is "Select"
        And  Search option is "TC_FIX_B2C"
        And  Search button with label "Search" is clicked
        And  First search result matching "TC_FIX_B2C" is checked
        And  Submit search results button "Verzenden" is clicked
        And  Modal dialog "Select" is not shown
        And  All date values at column "Geldig tot" from table "Bepaal het hernieuwingsproduct" are within the period "01-04-2020 31-03-2021"
