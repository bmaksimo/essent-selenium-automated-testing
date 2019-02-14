@B2C
@E2E

    Feature: Guarantee invoice
        @NUAT-5021-STEP-6-7
        Scenario: Cancel contract line of toekomstige klant and check if contract is cancelled

            # Step 6

            Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
            When Left menu is "contracting-switching"
            And Top menu item is "Klanten"
            And Top action is "Filters"
            And "Naam" input is "Dani Eldering"

            Given "1st" List element with value at column "Klantnummer & Naam" is checked
            And Click on "parameter:Klantnummer & Naam" link
            And Dashboard menu is "Contracten"
            Then "1st" List element with value at column "Contractnummer" is checked

            And Click on "parameter:Contractnummer" link
            And Plus actions at "1st" list row in the list "Contractlijnen" are open
            And List plus action is " Annuleer "
            Then Modal "Cancel contractline" is displayed

            And "Reden voor annulering" modal dropdown selection is "Geannuleerd door de klant"
            And Form is submitted
            Then "1st" list element has cell value "Geannuleerd" at column "Status & Product"

            # Step 7

            And Top arrow button is "up"
            Then "1st" list element has cell value "Geannuleerd (Waarborg)" at column "Type & status"

