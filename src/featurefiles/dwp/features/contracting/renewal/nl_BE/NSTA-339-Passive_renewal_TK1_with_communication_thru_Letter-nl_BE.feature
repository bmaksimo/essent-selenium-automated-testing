@ALL
@DWP
@B2C
@REGRESSION
Feature: NSTA-339 Passive renewal of contract TK1 - with communication through letter

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NSTA-339
    Scenario: Sign in to default electricity product
        #1. Onboarding of B2C customer, with TC1 quote and active contract.
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "35 days before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random | 1       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Electricity market mock mode is switched On on "Elektriciteit Vast" card
        And  EAN code is generated
        And "Startdatum" date is "35 days before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "35 days before now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #2. Trigger renewal batch
        #Actions
        #"Start & Einddatum" is parsed, start is put to parameterProvider as "Start & Einddatum - start", end - as "Start & Einddatum - end"
        When End of interval from "1st" row of table "Contracten" at column "Start & Einddatum" is checked
        #1.1. Collect jBilling customer Id
        And  Dashboard menu is "Details"
        And  Cell value at "1st" row at column "Id Billing customer" from table "Billing customer" is checked

        When Top arrow button is "Up"
        And Plus menu is "Contracting -> TK1 Hernieuwingen -> Hernieuwingsbatches"
        Then View list header is "TK1 - Hernieuwingsbatches" appears within 20 seconds

        When Click on "START NIEUWE HERNIEUWINGSBATCH" link
        Then Modal dialog is "Start passive renewal batch"
        When "Batchnaam" input is "parameter:suitecrm-customer-name"
        And  "Renewal date from" date is "parameter:Start & Einddatum - start"
        And "Renewal date to" date is "parameter:Start & Einddatum - end"
        And "EAN-code" input is "parameter:EAN-code-generated"
        Then Modal dialogue is confirmed
        And Table "TK1 - Hernieuwingsbatches" has matching value "parameter:suitecrm-customer-name" at column "Batchnaam"

        #Checks
        #3. Validate renewal batch
        # Actions
        When Click on "parameter:suitecrm-customer-name" link
        And  All cell values at "1st" row from table "Geselecteerde contractlijn voor hernieuwingsbatch" are checked
        And  PackageName is extracted as "1st" word from "parameter:Nieuw pakket/product"
        And  Click on "VALIDEER PASSIEVE HERNIEUWINGSBATCH" link
        And  Modal dialog is "Start passive renewal batch"
        And  Modal dialog contains "parameter:suitecrm-customer-name" in action list
        And  Modal dialogue is confirmed
        And  All cell values at "1st" row from table "Geselecteerde contractlijn voor hernieuwingsbatch" are checked


        #3.1. Validate renewal batch - checks
        Then "Status batch" field value is switched to "VALIDATED" within 30 seconds
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Gevalideerd" at column "Status hernieuwing"

        When Click on "parameter:Contractnummer" link
        And  Dashboard menu is "Sales"
        And Table "Offertes" has matching value "Passieve hernieuwing Geprijsd - Geaccepteerd" at column "Type & status"
        And Table "Offertes" has matching value "parameter:Contract Start & Einddatum" at column "Start & Einddatum"
        And Table "Offertes" has matching value "parameter:Id Billing customer" at column "Billing klant & Tariefdatum"

        #4 Validate the definition of renewal product (date valid within the period: "Start & einddatum hernieuwing")
        When Top arrow button is "Up"
        And  Plus menu is "Contracting -> TK1 Hernieuwingen -> Bepaal het hernieuwingsproduct"
        Then View list header is "Bepaal het hernieuwingsproduct" appears within 20 seconds

        When Top action is "Filters"
        And  Selection with search is "Van pakket"
        And  Modal dialog is "Select"
        And  Search option is "parameter:PackageName"
        And  Search button with label "Search" is clicked
        And  First search result matching "parameter:PackageName" is checked
        And  Submit search results button "Verzenden" is clicked
        And  Modal dialog "Select" is not shown
        And  All date values at column "Geldig tot" from table "Bepaal het hernieuwingsproduct" are within the period "parameter:Start & einddatum hernieuwing"


        #5 Communicate the renewal to the customer through the invoice
        When Plus menu is "Contracting -> TK1 Hernieuwingen -> Hernieuwingsbatches"
        Then View list header is "TK1 - Hernieuwingsbatches" appears within 20 seconds
        When Click on "parameter:suitecrm-customer-name" link
        And  Click on "VERSTUUR PASSIEVE HERNIEUWINGSBRIEVEN" link
        And Modal dialog is "Send passive renewal letter"
        And Modal dialogue is confirmed
        Then "Status batch" field value is switched to "LETTERS_SENT" within 120 seconds
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "verstuurd" at column "Status hernieuwing"
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Passief hernieuwd" at column "Offerte & status hernieuwing"

        #6 Check communication
        When Click on "parameter:Contractnummer" link
        And Dashboard menu is "Service"
        Then Table "Interacties" has matching value "Outbound document: Passive renewal communication" at column "Type & Onderwerp"
