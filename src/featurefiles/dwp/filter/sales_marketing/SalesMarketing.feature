@REGRESSION
@DWP
@FILTER
@BASIC
Feature: DWP Filter Options

    Background:
        Given I logged in as admin on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We see all Sales And Marketing Main Menu Filter elements
       When I check filter elements defined for Left Menu item: 'SALES_MARKETING'
           |Number|
           |Name|
    Scenario: We see all Sales And Marketing -> 'MARKETTRANSACTIONS_DASHBOARD' Filter elements
        When I click on the following Left Menu item: 'SALES_MARKETING'
        Then I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'MARKETTRANSACTIONS_DASHBOARD' Top Menu Item
            |EAN|
            |Process ID|
            |Parent process ID|
            |Cancellation reason|
            |Hijack info|
            |Rejection reason|
            |Company Name|
            |Company Number|
            |Account number|
            |Module|
            |Label|
            |EC Status|
            |Account segment|
            |BRP|
            |DGO|
            |Supplier|
            |Effective date after|
            |Effective date before|
            |Created after|
            |Created before|
        And I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'LEAD_LIST' Top Menu Item
            |Company name|
            |Only "do not call"|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'MY_ACCOUNTS_LIST' Top Menu Item
            |EAN|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'ACCOUNTS_LIST' Top Menu Item
            |EAN|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'QUOTES_LIST' Top Menu Item
            |Quote number|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'CONTRACT_LIST' Top Menu Item
            |Contract number|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'EUROCCOR_QUOTES' Top Menu Item
            |Quote number|
        And  I check filter elements defined for 'SALES_MARKETING' Left Menu Item and 'CASES' Top Menu Item
            |Case number|


