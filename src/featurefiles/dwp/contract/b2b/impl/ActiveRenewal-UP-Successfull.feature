@DWP
@BASIC
@CONTRACT
@RENEWAL
@REGRESSION

Feature: Test of active renewal of business contract.
    Active renewal of contract is interactive workflow.
    It is assumed that the customer is on the line and has he has provided the context
    of the workflow (Account Id or Contract Id).
    Pre-conditions
    - Tariff sheets are uploaded to SuiteCRM.
    - There is Customer account number available (Test set: 6574)
    - the customer has some Contracts expiring soon (${expDate} >= ${currentDate}+3months )to renew"
    Post-conditions

    Background:
        Given I logged in in DWP as BusinessDeskB2B

    Scenario:
        #Navigate to Customer Account
        When Left Tab is Contracting Switching
        And  Top Tab is Accounts
        And  Top Action is Filters
        #Dmitry
        And  Filter element "Account number" input is "6574"
        #Dmitry
        Then View List Header is "Accounts"
        #Navigate to Cockpit
        When Click on link in View List at 1st row and "Account Number & Name" column
        And  Cockpit item is Market Transactions
        And  View List Header is "Market Transactions"
        #Verify 3 contract acceptance criteria for a renewal.
        #Shluld be no one of following Market Transactions:
        #1) Loss
        #2) Initiate stop access - Residential drop
        #3) Non-Residential End-of-Contract
        And  Top Action is Filters
        And  Filter element "Module" selection is "LOSS"
        And  View List is empty
        And  Top Action is Filters
        And  Filter element "Module" selection is "INITIATE STOP ACCESS"
        And  Filter element "Label" selection is "Residential Drop"
        And  View List is empty
        And  Top Action is Filters
        And  Filter element "Module" selection is "INITIATE STOP ACCESS"
        And  Filter element "Label" selection is "Non-Residential End-of-Contract"
        Then View List is empty
        #Navigate to Renewal Details Card and Fill In Renewal Details
        When Top Action is Home
        And Left Tab is Contracting Switching
        And Top Tab is Contracts
        And Top Action is Filters
        And  Filter element "Contract type" selection is "All values"
        And  Filter element "Account number" input is "6574"
        And  Filter element "End date from" date input is "$today + 3 months"
        And  Filter element "End date to" date input is "$today +  4 months"
        #Dmitry
        And  Select "1" List rows at:
            |column                     | value           |
            |COMPANY NAME & CONTACT     | Cavamil  (6574) |
        And  List Plus Action is RENEW
        Then Submit Card is RENEWAL DETAILS

        #Fill in & submit the Renewal and Calculation Card
        #Select the Tariefgroep 'Up' - Unique pricing or "TK2" - Tarif card  based on 2-weeks period
        #---TK2 will limit the available products to 2 year products only and fixed price
        #---UP - all the products will be available in the combo box
        #Normally selection is of Fixed price product
        #Exceptionally one cal select variable on
        #---UP one can select 1, 2, 3, years of duration.
        #---UP: one one can also select the marge.
        #The margin should be explicitly defined for each test execution.
        #High (Dag)
        #Low (Nacht)
        #Fixed fee (vaste vergoeding)
        #Chris
        When Tariff group is UP
        #Chris
        And High is 1000
        #Chris
        And  Fixed fee
        And Top Action is Confirm
        Then View List Header is "Quote"
#Send the Renewal comminication to myself
        When Select "1" List rows at:
            |column                     | value             |
            |TYPE & STATUS              | Priced - Accepted |
        And List Plus Action is  Send to Customer
        And Popup dialogue is Send quote to customer
        #Valid values are 'Do', 'Don't'
        And 'Do' Send e-mail to me
        And Confirm Send quote to customer popup dialogue
        Then Select "1" List rows at:
            |column                     | value             |
            |TYPE & STATUS              | Sent to customer - Accepted |
        #Pdf validation is to be tasks for next sprints
        #I click on Documents in the cockpit
        #I open the PDF
        #I  formally check the PDF
        #I check the price
        #I check the dates
        #I check the client data
        #I check the name
        #I check the fixed fee
        #I check the conditions (which ones)



