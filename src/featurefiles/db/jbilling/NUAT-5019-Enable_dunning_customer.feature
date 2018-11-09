@DWP
@E2E
@CREDIT-AND-CONTROL
Feature: NUAT-5019-2: White-list a customer for dunning process by SuiteCRM customer ID

    @PREPARE-DUNNING-CUSTOMER
    Scenario: Prepare dunning customer
        When Dunning customer is "1000019227"
