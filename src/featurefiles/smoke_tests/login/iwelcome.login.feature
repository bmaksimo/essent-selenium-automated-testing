@SMOKE
    @LALALA
Feature: Javascript DWP testing

    Background:
#        Logging in will fail outside of iWelcome environments, like DEVINT01
        Given   I logged in in DWP as d.chebayewski.billinghouse@essent.be
        And     I optionally discard a previous flow

    Scenario:
        When Left Menu Item is sales-marketing
        Then Top Menu Item is Market Transactions

