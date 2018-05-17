@DWP
@BASIC
@CONTRACT
@RENEWAL
@REGRESSION

Feature: Test of UI DWP workflow that is triggering the passive renewal of business contract.
    Contrary to Active one, Passive renewal does not assume any actions of Customer.
    The User, Business Service Employee, selects the contracts with due date +3 monts from current date and trigger "Passive renewal".
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
        Given I execute data import batch that imports Prices
        And   I logged in as Business Service Desk Employee on the DWP Main Page
    Scenario:
        ## UP TK 2 stands for: Unit Pricing Tarif Kaart 2
        When  I start Contracting - UP TK2 Flow
        And   I apply '${today} + 3 monts - 3 days' value to 'End date from' filter element defined for 'CONTRACTING_SWITCHING' Left Menu Item and 'CONTRACT_LIST' Top Menu Item
        And   I apply '${today} + 3 monts + 3 days' value to 'End date from' filter element defined for 'CONTRACTING_SWITCHING' Left Menu Item and 'CONTRACT_LIST' Top Menu Item
        And   I select contracts with '${all}' indices
        And   I click on 'Passive Renewal'
        Then  I go to 'Contracting UP TK2 Passive renewal Offers'
        And   I see the UP/TK2 Passive renewals
        ##  The step has to be parameterized
        And   The number of contracts is same as we submitted before
        And   I select contracts with '${all}' indices
        And   I click on 'confirm UP / TK2 / Contract renewal'
        ## The step has to be parameterized
        Then  I see previously selected UP/TK2 Passive renewals renewed
        ## Additionally - verify the receipt of email?





