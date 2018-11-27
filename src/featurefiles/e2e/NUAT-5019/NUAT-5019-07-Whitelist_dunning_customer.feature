@DWP
@E2E
@B2C
@CREDIT-AND-CONTROL
Feature: NUAT-5019 Step 7. White-list a customer with given SuiteCRM customer Id for soft dunning process

    @ENABLE-DUNNING-CUSTOMER
    @NUAT-5019-STEP-7
    Scenario: Prepare dunning customer
        When Customer with CRM Id 1000026076 is added to dunning whitelist
