@DWP
@SOCTAR
Feature: Soctar batch contract creation

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @SOCTAR-06-07
    Scenario: Add Soctar contract and check if type and status are changed
        Given Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
        And Click on link in View List at "1st" row and "Batchnaam" column
        #Step 6. Create contract and check if batch is done
        When Soctar batch action "CONTRACTEN AANMAKEN OP BASIS VAN OFFERTES" is clicked
        Then Soctar type is changed to "Create Contracts" within 10 seconds
        And Soctar status is changed to "DONE"
        #Step 7. Check if contract has been created
        And "1st" list element has cell value "Verwerkt" at column "Status"
        And "1st" list element has cell value "parameter:soctar-contract-dates" at column "Contractnummer & start- en einddatum"
