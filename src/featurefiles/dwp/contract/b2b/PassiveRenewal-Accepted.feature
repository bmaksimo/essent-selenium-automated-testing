@DWP
@BASIC
@CONTRACT
@RENEWAL
@REGRESSION

Feature: Test the passive renewal of business contract.
<<<<<<< HEAD
    Contrary to Active renewal, the Passive renewal workflow does not involve any interaction with Customer.
=======
    Contrary to Active renewal, the Passive renewal process does not involve any interaction with Customer.
>>>>>>> d34ce2b2a3f211b714fd34c7f7376b9ba3690b9a
    The User, Business Service Employee, selects the contracts with due date +3 months from current date and triggers "Passive renewal".
    #
    ## Pre-conditions
    #  Given the batch process which imports following data:
    #  Prices that are relevant for to-renew contracts
    #    and validated by SMP (portfolio manager)
    #    and valid until new "end of market" (to clarify the jargon once more with SME)
    #  Contract(s) with due date +3 months from curent date;
    #  Customer(s) with solvency  > 30% (Essent criterion that is likely to be variable)
    #  has been successfully executed
    #
    #
    #
    ## We expect
    #  Then the Contract is displayed in renewal list
    #  And email about recurring renewal process is dispatched to the Customer
    #
    ##  Important detail: The process is not only passively renewing the contract,
    #   it checks the actual solvency value (currently Essent service partner is graydon.be)
    #   So until the point where the contract renewal has to be confirmed, the process is re-usable for different scenarios depending on
    #   status of the contract / customer. There could be following statuses: Accepted, Refused, Guarantee (Waarborg)
    #   "Guarantee" involves very elaborate checks and therefore it is always tested manually.
    #   On the contrary, "Accepted" and "Refused" are very straight-forward, and should be therefore automated.

    Background:
        Given Imported data is 'Prices'
        And   I logged in as 'Business Desk' on the DWP Main Page
    Scenario:
        ## UP TK 2 stands for: Unit Pricing Tarif Kaart 2
        #Start Contracting - UP/TC2 - to renew contracts
        When Left Tab is Contracting
        And  Top Tab is Contracts
        And  Top Action is Plus Menu
        And  Plus Menu is Contracting - UP/TC2 - to renew contracts
        And  Top Action is Filter
        #Valid Parameters are: Accepted,  Refused
        And  Filter element 'Acceptance status' is 'Accepted'
        And  Filter element 'End date from' is '${today} + 1 month'
        And  Filter element 'End date to' is '${today} + 3 months'

         #DC implements this step
        Then Redirect view is UP-TC2 - to renew contracts
        #Possible parameters values are: 'all'
            # or '{comma-separated numbers} where number is: 1st, 2nd, 3rd'
            # or, optionally, range of numbers: '1st thru 3rd'

        When  I select '1st, 2nd, 3rd' contracts from the list
        And   I store the selected contracts as 'SelectedContractsOutParam'
        And   Table action is 'PASSIVE RENEW'
        And   Redirect view is UP-TC2 - to renew contracts
        And   Top Action is Plus Menu
        And   Plus Menu is UP/TC2 - Passive renewal quotes
        Then  Redirect view is UP/TC2 - Passive Renewal quotes


        ##  The step has to be parameterized
        And   Quotes are 'Created' for 'SelectedContractsOutParam'
        When  I select 'SelectedContractsOutParam'
        And   Table action is 'CONFIRM PASSIVE RENEW'
        And   I confirm 'CONFIRM PASSIVE RENEWAL'
        Then  Redirect view is UP/TC2 - Passive Renewal quotes
        And   Quotes are 'Not created' for 'SelectedContractsOutParam'
        ##    Optionally - And renewal email with 'ContractNumber' Subject is received
        And   The number of contracts is same as we submitted before
        And   I select contracts with '${all}' indices
        And   I click on 'confirm UP / TK2 / Contract renewal'
        ## The step has to be parameterized
        Then  I see previously selected UP/TK2 Passive renewals renewed
        ## Additionally - verify the receipt of email?
