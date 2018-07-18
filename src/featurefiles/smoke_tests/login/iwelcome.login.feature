@SMOKE
Feature: Javascript DWP testing

    Background:
#        This test will fail outside iWelcome
        Given   I logged in in DWP as d.chebayewski.billinghouse@essent.be
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        Then Top Menu Item is Market Transactions

