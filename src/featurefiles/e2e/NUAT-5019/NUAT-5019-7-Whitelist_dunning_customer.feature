@DWP
@E2E
@B2C
@CREDIT-AND-CONTROL
Feature: NUAT-5019-2: White-list a customer for dunning process by SuiteCRM customer ID

    @ENABLE-DUNNING-CUSTOMER
    Scenario: Prepare dunning customer
        When Customer with CRM Id parameter:Klantnummer & Naam is added to dunning whitelist
