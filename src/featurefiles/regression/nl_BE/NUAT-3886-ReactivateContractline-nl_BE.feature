@DWP
@REGRESSION
@B2B
@LONGDURATION
@UNSTABLE
Feature: NUAT-3886 Reactivate contractline

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-3886
    Scenario: Reactivate contract line
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
        And "Testing" turn on
        And "Market mock" turn on
        Then Changes are confirmed
        And View list header is "Marktberichten" appears within 10 seconds

        When First list element with value "INITIATE STOP ACCESS" at column "Module & Label" has status "Geaccepteerd" at column "Status & ED" within 450 seconds refreshing "REFRESH MARKTBERICHTEN"
        And Dashboard menu is "Contracten"
        And "1st" List element with value at column "Contractnummer" is checked
        And Click on "parameter:Contractnummer" link
        And Plus actions at "1st" list row having cell value "Any" at column "Any" are open
        And List plus action is "Reactiveer contractlijn"
        And "Nieuwe startdatum" date is "now"
        And Label "Mig module" is "START ACCESS"
        And Changes are confirmed
        Then Contract is in "Te activeren" state
