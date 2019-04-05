@DWP
@B2C
@SOCTAR
Feature: NSTA-333: Sent out the confirmation letter

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @SOCTAR-08-10
    @NSTA-333-STEP-8-10
    Scenario: Check if Soctar confirmation has been done correctly

    #Step 8 Sent out the confirmation letter

        When Plus menu is "Contracting -> Soctar -> Sociaal tarief contractlijnen"
        And "EAN-code" input is "parameter:EAN-code"
        Then "1st" List element with value at column "Status & Product" is checked
        And Click on "BEVESTIG CONTRACTLIJNEN" link
        Then Changes are confirmed

    #Step 9 Check batch SOCTAR confirmation letter

        When Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
        And Click on link in "Soctar Confirmation Letters" View List at "1st" row and "Batchnaam" column
        Then Soctar tariff type and status are "Confirmation" - "DONE"
        When Top arrow button is "UP"

    #Step 10 Check if all changes are correct on the customer
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"

        #part 1 check - customer status
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:Klantnummer & Naam"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        When Dashboard menu is "Contracten"
        And Table "Contracten" contains value "Verwerkt (Geaccepteerd)" at column "Type & status"
        And Table "Contracten" contains value "Inactief (Geaccepteerd)" at column "Type & status"

        #part 2 check - contract is soctar and start date matches
        And Table "Contracten" contains value "sociaal tarief (SOCTAR)" at column "EAN-codes & Producten"
        And Table "Contracten" contains value "parameter:start-en-einddatum" at column "Start & Einddatum"

        #part 3 check - protected record
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        When Plus action of "1" element from "ContractlinesOnContract" and click on "View protected"

        And Table "Protected records" contains value "Automatic" at column "Type"
        And Table "Protected records" contains value "parameter:start-en-einddatum" at column "Start & End Date"
        Then Clicked on sign X

        #part 4 check - letter has been sent
        When Dashboard menu is "Service"
        And Table "Interacties" contains value "Recal_ext_recal_credit" at column "Type & Onderwerp"







