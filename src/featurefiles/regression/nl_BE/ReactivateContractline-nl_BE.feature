@REGRESSION
@J
@LONGDURATION
Feature: Reactivate contractline

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
#        And Create a new active account
        And "Klantnummer" input is "parameter:accountNumber"
        Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
#        And "Klantnummer" input is "1000001223"
#        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

        When Dashboard menu is Contracten
        And Save EAN from active contract
        And Dashboard menu is Marktberichten
        And Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Search by "parameter:EAN-active-contract"
        And Changes are confirmed
        And Label "Module" is "INITIATE STOP ACCESS"
        And Label "Label" is "Non-Residential End-of-Contract"
        And "Testing" turn on
        And "Market mock" turn on
        Then Changes are confirmed

        When Refresh "REFRESH MARKTBERICHTEN" till "Geaccepteerd" is visible
        And Dashboard menu is Contracten
        And Click on link in "ContractsOnAccount" View List at 1 row and "Nummer & Aanmaakdatum" column
        And Plus action and "Reactiveer contractlijn" of first customer from list
#        And Plus action of "1" element from "ContractsOnAccount" and click on "Reactiveer contractlijn"
        And "Nieuwe startdatum" date is "now"
        And "Einddatum" date is "96 week from now"
#        And "Einddatum" date is "24 months from now"
        And Label "Mig module" is "START ACCESS"
        And Changes are confirmed
        Then Contract is in "Te activeren" state



#//validation-wrapper[@label='Module"']/div/div/ng-form/div/select-form-element/div/select/option[@label=''INITIATE STOP ACCESS"]"

#    name="START NIEUW MARKTBERICHT"

# parameter:EAN-active-contract
#        When Top action is Filters
#        And "Status EC" input is "Post-pr
#
# ocessing period"

#
#        And Dashboard menu is Contracten
#
#        #And Click on fist in Contractlijnen
#
#        And Plus action and "Reactiveer contractlijn" of first customer from list
#        And Mig module is "START ACCESS"
#        And Mig label is "Combined customer switch"
#        And Nieuwe startdatum is today
#        And Eindatum is "09/11/2020"

#        And Label input for "Mig module" is "START ACCESS"
#        And Label input for "Mig label" is "Combined Customer Switch"

#    ean //*[@id="rows"]//list-link-bold-top-two-liner-cell//a/h5
