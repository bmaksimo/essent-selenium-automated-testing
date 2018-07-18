@SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        Then Top Menu Item is Market Transactions

