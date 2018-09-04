@B2B_REGRESSION
Feature: Log questions of customers

    Background:
        Given   I logged in to DWP as b.maksimovic@levi9.com


    Scenario:
        When pgo Account is selected
        When pgo New case for customer is logged
        Then pgo Case details are visible when case is opened
        
