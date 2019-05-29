@DWP
@SERVICE-CONTRACTING
@SOCTAR
@REGRESSION
@API
@ALL
Feature: NSTA-333: Social tariff (SOCTAR) contract creation

    Background:
        # 1 - API contract creation
        Given I login to iWelcome as "soapui_b2c"
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "Off"
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
    @SOCTAR-COMPLETE
    @NSTA-333
    Scenario: Create Soctar (Social tariff) quote and contract, and check Soctar confirmation letter
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        And Soctar start date is "now"

        #Steps 2 - Soctar file sftp upload
        Given Soctar customer Id is "parameter:accountNumber"
        And Soctar EAN is "parameter:EAN-code"
        And Soctar start date is "now"
        Then Soctar file is uploaded to "/home/ESSENT/sa_sftpcrm_smx/data/soctar" remote directory

        #Step 3 Check the status of "Soctar file upload"
        When Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
        Then "1st" list element has cell value "parameter:soctar-file-name" at column "Batchnaam" within 450 seconds
        And "1st" list element has cell value "Import Klaar" at column "Type & Status"

        #Step 4 Check the status of "Soctar file import"
        Given Click on "parameter:soctar-file-name" link
        Then Soctar tariff type and status are "Import" - "DONE"

        #Step 5. Check the status of Social tariff quote
        When "1st" list element has cell value "parameter:EAN-code" at column "EAN-code"
        Then  "1st" list element has cell value "Quote Created" at column "Status"
        And   "1st" list element has cell value "parameter:start-en-einddatum" at column "Contractnummer & start- en einddatum"

        #Step6
        When Soctar batch action "CONTRACTEN AANMAKEN OP BASIS VAN OFFERTES" is clicked
        Then Soctar type is changed to "Create Contracts" within 30 seconds
        And "Status" field value is "DONE"
        #Step 7. Check if contract has been created
        And "1st" list element has cell value "Verwerkt" at column "Status"
        And "1st" list element has cell value "parameter:start-en-einddatum" at column "Contractnummer & start- en einddatum"

        #Step 8 Sent out the confirmation letter
        When Top arrow button is "UP"
        And Left menu is "contracting-switching"
        And Plus menu is "Contracting -> Soctar -> Sociaal tarief contractlijnen"
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
