@DWP
@E2E
@CREDIT-AND-CONTROL
Feature: NUAT-5019-2: White-list a customer for dunning process by SuiteCRM customer ID

    @ENABLE-DUNNING-CUSTOMER
    Scenario: Prepare dunning customer
        When Dunning customer CRM Id is parameter:Klantnummer & Naam
