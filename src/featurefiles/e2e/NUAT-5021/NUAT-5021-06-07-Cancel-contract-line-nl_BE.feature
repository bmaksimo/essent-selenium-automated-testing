@B2C
@E2E

    Feature: Guarantee invoice
        @NUAT-5021-STEP-6-7
        Scenario: Create guarantee invoice after customer deduplication

            Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
            When Left menu is "contracting-switching"
            And Top menu item is "Klanten"
            And Top action is "Filters"
            And "Naam" input is "usman rietveld"

            Given "1st" List element with value at column "Klantnummer & Naam" is checked
            And Click on "parameter:Klantnummer & Naam" link
            And Dashboard menu is "Contracten"
            Then "1st" List element with value at column "Contractnummer" is checked
            And Click on "parameter:Contractnummer" link

            Given "1st" List element with value at column "EAN-code & Metertype & Configuratie" is checked
            When Plus actions at "1st" list row having cell value "parameter:EAN-code & Metertype & Configuratie" at column "EAN-code & Metertype & Configuratie" are open
            And List plus action is "Annuleer"


