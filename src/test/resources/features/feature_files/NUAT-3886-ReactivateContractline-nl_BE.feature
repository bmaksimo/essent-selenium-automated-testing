@ALL
@DWP
@REGRESSION
@B2B
@UAT08ONLY
Feature: NUAT-3886 Sent out non-residential End of contract (EOC)

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-3886
    Scenario: Sent out non-residential End of contract (EOC)
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And Save EAN from active contract
        And Dashboard menu is "Marktberichten"
        And Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Search by "parameter:EAN-active-contract"
        And Changes are confirmed
        And Label "Module" is "INITIATE STOP ACCESS"
        And Label "Label" is "Non-Residential End-of-Contract"
        And "Effective Date" date is "1 month from now"
        And  Option "Testing?" "is" "On"
        And Changes are confirmed
        Then View list header is "Marktberichten" appears within 10 seconds
        And Click on "INITIATE STOP ACCESS" link
