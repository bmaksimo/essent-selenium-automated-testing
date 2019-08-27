@ALL
@API
@DWP
@B2C
@REGRESSION
Feature: NSTA-339 Passive renewal of contract TK1 - with communication through letter

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-339
    Scenario: Sign in to default electricity product
        #Create contract via API
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"

        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"

        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        And Contracted EAN exists on account

        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Top action is Filter from "contracting-switching" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds

        When Dashboard menu is "Contracten"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

        #2. Trigger renewal batch
        #"Start & Einddatum" is parsed, start is put to parameterProvider as "Start & Einddatum - start", end - as "Start & Einddatum - end"
        When End of interval from "1st" row of table "Contracten" at column "Start & Einddatum" is checked
        #1.1. Collect jBilling customer Id
        And  Dashboard menu is "Details"
        And  Cell value at "1st" row at column "Id Billing customer" from table "Billing customer" is checked

        When Top arrow button is "Up"
        And Plus menu is "Contracting -> TK1 Hernieuwingen -> Hernieuwingsbatches"

        When Click on "START NIEUWE HERNIEUWINGSBATCH" link
        Then Modal dialog is "Start passive renewal batch"
        When "Batchnaam" input is "parameter:suitecrm-customer-name"
        And  "Renewal date from" date is "parameter:Start & Einddatum - start"
        And "Renewal date to" date is "parameter:Start & Einddatum - end"
        And "EAN-code" input is "parameter:EAN-code"
        Then Changes are confirmed
        And Table "TK1 - Hernieuwingsbatches" contains value "parameter:suitecrm-customer-name" at column "Batchnaam" waiting for 25 seconds

        #Checks
        #3. Validate renewal batch
        When Click on "parameter:suitecrm-customer-name" link
        And  All cell values at "1st" row from table "Geselecteerde contractlijn voor hernieuwingsbatch" are checked
        And  PackageName is extracted as "1st" word from "parameter:Nieuw pakket/product"
        And  Click on "VALIDEER PASSIEVE HERNIEUWINGSBATCH" link
        And  Modal dialog is "Valideer contractlijnen hernieuwing"
        And  Modal dialog contains "parameter:suitecrm-customer-name" in action list
        Then Changes are confirmed
        And  All cell values at "1st" row from table "Geselecteerde contractlijn voor hernieuwingsbatch" are checked

        #3.1. Validate renewal batch - checks
        Then "Status batch" field value is switched to "VALIDATED" within 60 seconds
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Gevalideerd" at column "Status hernieuwing"

        When Click on "parameter:Contractnummer" link
        And  Dashboard menu is "Sales"
        And Table "Offertes" has matching value "Passieve hernieuwing Geprijsd - Geaccepteerd" at column "Type & status" polling 10 seconds
        And Table "Offertes" has matching value "parameter:Contract Start & Einddatum" at column "Start & Einddatum" polling 10 seconds
        And Table "Offertes" has matching value "parameter:Id Billing customer" at column "Billing klant & Tariefdatum" polling 10 seconds

        #4 Validate the definition of renewal product (date valid within the period: "Start & einddatum hernieuwing")
        When Top arrow button is "Up"
        And  Plus menu is "Contracting -> TK1 Hernieuwingen -> Bepaal het hernieuwingsproduct"

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
        When Click on "parameter:suitecrm-customer-name" link
        And  Click on "VERSTUUR PASSIEVE HERNIEUWINGSBRIEVEN" link
        And Modal dialog is "Send passive renewal letter"
        And Changes are confirmed
        Then "Status batch" field value is switched to "LETTERS_SENT" within 180 seconds
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "verstuurd" at column "Status hernieuwing"
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Passief hernieuwd" at column "Offerte & status hernieuwing"

        #6 Check communication
        When Click on "parameter:Contractnummer" link
        And Dashboard menu is "Service"
        Then Table "Interacties" has matching value "Outbound document: Passive renewal communication" at column "Type & Onderwerp" polling 10 seconds
