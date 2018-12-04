@DWP
@REGRESSION
@B2B
@LONGDURATION
Feature: NUAT-3886-Reactivate contractline

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is Contracten
        And Save EAN from active contract
        And Dashboard menu is Marktberichten
        And Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Search by "parameter:EAN-active-contract"
        And Changes are confirmed
        And Label "Module" is "INITIATE STOP ACCESS"
        And Label "Label" is "Drop/Request Budget Meter"
        And "Testing" turn on
        And "Market mock" turn on
        Then Changes are confirmed

        When Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible in table
        Then Marketbirich with EAN "parameter:EAN-active-contract" and module "INITIATE STOP ACCESS" is in status "Geaccepteerd"
