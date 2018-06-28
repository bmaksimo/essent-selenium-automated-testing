@DWP
@BASIC
@CONTRACT
@_RENEWAL
@REGRESSION

Feature: Test the passive renewal of B2B contract.

    Background:
        Given   I logged in in DWP as BusinessDeskB2B

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP/TC2 - to renew contracts"
        Then View List Header is "UP-TC2 - to renew contracts"

        #Selecting and submitting to renew contracts
        When Top Action is Filter
        #Valid values: Accepted,  Refused
        And  Filter element "Acceptance status" selection is "Accepted"
        #Valid value is '' - empty string or Default
        And  Filter element "Contract line status" selection is ""
        And  Filter element "End date from" input is "${today} + 3months"
        And  Filter element "End date to" input is "${today} +  4months"
        #Valid values are Sales Signed (Not marked), Sales Signed ()
        And  Select 2 List rows having cell value Closed at column EC Status & Effective Date
        And  Store cell values of selected list rows at column "COMPANY NAME & CONTACT" as "selected_contracts"
