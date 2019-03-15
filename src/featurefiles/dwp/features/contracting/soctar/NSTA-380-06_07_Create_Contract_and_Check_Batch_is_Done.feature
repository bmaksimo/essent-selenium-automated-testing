@DWP
@E2E
@SOCTAR
Feature: NUAT-5019 Complete E2E scenario "Active customer to drop, through one payment and 3 dunning levels, with SS and Market Mock"

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @SOCTAR-06-07
    Scenario: Create active contract that after dunning the contract becomes inactive
        Given Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
        And Click on link in View List at "1st" row and "Batchnaam" column
        #Step 6. Create contract and check if batch is done
        When Soctar batch action "CONTRACTEN AANMAKEN OP BASIS VAN OFFERTES" is clicked
        Then Soctar type is changed to "Create Contracts" within 10 seconds
        And Soctar status is changed to "DONE"
        #Step 7. Check if contract has been created
        And "1st" list element has cell value "Verwerkt" at column "Status"
#        And "1st" list element has cell value "parameter:soctar-contract-dates" at column "Contractnummer & start- en einddatum"
        And "1st" list element has cell value "29-01-2019 - 31-12-2019" at column "Contractnummer & start- en einddatum"
