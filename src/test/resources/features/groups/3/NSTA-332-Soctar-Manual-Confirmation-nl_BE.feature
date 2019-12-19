@DWP
@B2C
@REGRESSION
@SOCTAR-CONFIRMATION
@ALL
Feature: NSTA-332: Manual Soctar confirmation

    Background:
        Given I login as API user "soapui_b2c"

    @ONBOARDING
    @NSTA-332
    Scenario: NSTA-332: Manual Soctar confirmation
        #Step 1: Create active contract
        And Create active B2C contract with metering "On" and sign date "35 days before now"

        #Step 2: Change contract to SOCTAR
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is Filter from "contracting-switching" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"

        When Click on "parameter:accountNumber" link
        When Dashboard menu is "Contracten"
        Then Get Start Date
        When Plus action of "1" element from "ContractsOnAccount" and click on "TK1 Soctar Productwijziging"
        And "Datum attest" date is "3 days before now"
        Then Populate Soctar with dates "parameter:startDate" and "parameter:inputValue"
        And "Startdatum nieuwe offerte" date is "parameter:StartDateByQuarter"
        And "Einddatum nieuw contractvoorstel" date is "parameter:EndDateByYear"
        Then Changes are confirmed
        Then Bevestigen

        #Step 3: Check SOCTAR product change
        When Dashboard menu is "Contracten"
        And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        And Table "Contracten" contains value "Geannuleerd (Geaccepteerd)" at column "Type & status"
        And Table "Contracten" contains value "Verwerkt (Geaccepteerd)" at column "Type & status"
        And Table "Actieve en toekomstige connecties" contains value "sociaal tarief (SOCTAR)" at column "EAN-code"


