@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


    Scenario:
        When b2b Account is selected by using filter on account page
        And b2b New case for customer is logged through plus icon on the top right side
        Then b2b Case details are visible when case is opened
        
