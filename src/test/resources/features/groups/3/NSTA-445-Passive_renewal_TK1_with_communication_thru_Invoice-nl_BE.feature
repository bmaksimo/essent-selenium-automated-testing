@ALL
@API
@DWP
@B2C
@REGRESSION
Feature: NSTA-445 Passive renewal of contract TK1 - with communication through Invoice

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-445
    Scenario: Sign in to default electricity product
        #Contract creation via API
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "On" and sign date is "35 days before now"
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
        When Top action is "Filters"
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
        And Table "TK1 - Hernieuwingsbatches" has matching value "parameter:suitecrm-customer-name" at column "Batchnaam"

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
        Then "Status batch" field value is switched to "VALIDATED" within 120 seconds
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Gevalideerd" at column "Status hernieuwing"

        When Click on "parameter:Contractnummer" link
        And  Dashboard menu is "Sales"
        And Table "Offertes" has matching value "Passieve hernieuwing Geprijsd - Geaccepteerd" at column "Type & status"
        And Table "Offertes" has matching value "parameter:Contract Start & Einddatum" at column "Start & Einddatum"
        And Table "Offertes" has matching value "parameter:Id Billing customer" at column "Billing klant & Tariefdatum"

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
        Given I renew login to DWP as "billing.testautomation@essent.be"

        When Plus menu is "Billing -> Start facturatierun"
        When Modal dialog is "Start invoicerun"
        And "Factuurdatum" date is "now"
        And "Procesdatum" date is "now"
        And "Naam job" selection is "recurrent"
        And "ID Billing customer" input is "parameter:Id Billing customer"
        Then Invoice run is scheduled

        And Sleep for 15 seconds
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        And "1st" list element has cell value "Invoice (ADVANCE)" at column "ID & Type" polling 450 seconds

        #6 Check communication
        When Dashboard menu is "Service"
        Then Table "Interacties" has matching value "Outbound document: Passive renewal communication" at column "Type & Onderwerp"
        And  Table "Interacties" has matching value "Outbound document: advance" at column "Type & Onderwerp"
