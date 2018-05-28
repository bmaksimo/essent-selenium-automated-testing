@DWP
@BASIC
@_CONTRACT
@_RENEWAL
@REGRESSION

Feature: Test of active renewal of business contract.
    Contrary to the Passive renewal process, Active renewal involves interaction with Customer.
    It is assumed that the Customer is on the line, assisting the Business Desk Employee.
    The DWP User, Business Desk Employee, selects the contract.
    In real execution scenario, the selection of contract is assisted by the Customer,
    in automated scebnario, prepared contract is selected.
    Background:
        Given I execute data import batch that imports Prices
        And   I logged in as 'Business Desk' on the DWP Main Page
    Scenario:
        When I click on the following Left Menu item: 'SERVICE'
        And  I start Contracting - UP TK2 - 'Active' renewal



