@DWP
@BASIC
@CONTRACT
@RENEWAL
@REGRESSION

Feature: Test the passive renewal of business contract.
    Passive renewal workflow is executed without interaction with Customer.
    Pre-conditions of test execution
     - Tariff sheets are uploaded to SuiteCRM.
     - Tariff sheets should contain TK2 for B2B, and they should be validated by portfolio manager.
    Post-conditions (what is expected):
    - New Passive Renewal Quote is created;
    - Recurring communication, "Passive Renewal",  is dispatched to the Customer.
    There are Customers with acceptance status values: Accepted, Refused, Guarantee (Waarborg).
    "Guarantee" involves very elaborate checks before determining whether the contract can be passively renewed,
    therefore even the passive renewal "Guarantee" acceptance status is checked manually.
    "Accepted" and "Refused" cases, are, on the contrary, very straight-forward, and the renewal check can be automated.
    This scenario checks successful renewal for the customers with "Accepted" acceptance status.
    Potentially useful notes.
    The workflow not only passively renews the contracts.
    It checks the actual solvency of the Customer (recently, the service provider is graydon.be).
    The scenario can be potentially re-used for validating the acceptance of the customer
    (it is seen eventually in the error dialogue, "the contract cannot be passively renewed").

    Background:
        Given   I logged in in DWP as BusinessDeskB2B
         #Valid parameters are 'N(min|sec)'
        And Recent Time interval is '5min' backward
    Scenario:
        When Left Tab is Contracting
        And  Top Tab is Contracts
        And  Top Action is Plus Menu
        And  Plus Menu is Contracting - UP/TC2 - TO RENEW CONTRACTS
        Then View is UP-TC2 - to renew contracts

        #Selecting and submitting to renew contracts
        When Top Action is Filter
        #Valid values: Accepted,  Refused
        And  Filter element 'Acceptance status' is 'Accepted'
        #Valid value is '' - empty string or Default
        And  Filter element 'Contract line status' is ''
        And  Filter element 'End date from' is '${today} + 3months'
        And  Filter element 'End date to' is '${today} +  4months'
        #Valid values are Sales Signed (Not marked), Sales Signed ()
        And  Select 2 rows with
        |column                     | value                     |
        |TYPE & (RENEW) STATUS      | Sales Signed (Not marked) |
        And  Store selection values of 'COMPANY NAME & CONTACT' columns as comma-separated $SelectedContracts
        And  Table action is 'PASSIVE RENEW'
        #Phrase means "Rows, selected 3 steps back but column values have changed"
        Then Rows with 'COMPANY NAME & CONTACT' column having $SelectedContracts are 'Available' with
        |column                     | value                     |
        |TYPE & (RENEW) STATUS      | Sales Signed (Passive renewal (with communication)) |
        #Confirming to renew contracts
        When   Top Action is Plus Menu
        And   Plus Menu is UP/TC2 - Passive renewal quotes
        Then  View is UP/TC2 - Passive Renewal quotes
        And   Select rows with 'ACCOUNT & CONTACT PERSON' column having $SelectedContracts with
        |column                     | value                             |
        |TYPE & STATUS              | Renewal-passive Priced - Accepted |
        And   Table action is 'CONFIRM UP/TC2 PASSIVE RENEWALS'
        And   Popup dialogue is CONFIRM PASSIVE RENEWAL
        And   Confirm 'CONFIRM PASSIVE RENEWAL' popup dialogue
        Then  View is UP/TC2 - Passive Renewal quotes
        #Valid values: "Available", "Not available", "Gone"
        But   Rows with 'COMPANY NAME & CONTACT' column having $SelectedContracts are 'Gone'
        #Checking post-condition "New quote - passive renewal is created"
        When  Left Menu Item is Contracting Switching
        And   Top Menu Item is Quotes
        Then  View is Quotes
        And   Select rows with 'ACCOUNT & CONTACT PERSON' column having $SelectedContracts with
        |column                     | value                             |
        |TYPE & STATUS              | Renewal-passive Priced - Accepted |
        #Checking post-condition "Recurring Passive Renewal communication is dispatched to the Customer"
        When I select the 1st element of $SelectedContracts and click the link in the "Number & Signed contract nr" column
        And Overview is Documents
        And View is Documents
        #Valid values are 'Available', 'Not Available' and 'Gone'
        Then Rows are 'Available' with
         |column                       | value                             |
         |DOCUMENT TYPE                | Passive renewal communication     |
         |CREATION DATE                | $recentTime                       |





