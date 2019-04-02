@DWP
@B2C
@SOCTAR
Feature: Check the status of Soctar file upload and impor
    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        #Output parameter "start_end_date", format: '1yyyyMMddyyyy1231'
        #Output parameter  "start-en-einddatum", format: 'dd-MM-yyyy - dd-MM-yyyy'
        And   Soctar start date is "now"
    @SOCTAR-03-05
    @NSTA-333-STEP-3-5
    Scenario: Upload Soctar file to Nova sftp
        When Left menu is "contracting-switching"
        And Top menu item is "Klanten"
        And Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
         #Output parameter: "plus-menu-item"
        #Step 3
        When Plus menu is "Contracting -> Soctar -> Sociale tariefbatches"
        #Input parameter: "parameter:soctar-file-name"
        #Input parameter   "plus-menu-item"
        #Step will refresh the view, clicking on "plus-menu-item"
        Then "1st" list element has cell value "soctar-1000055343-541442938381222400.csv" at column "Batchnaam" within 450 seconds
        And  "1st" list element has cell value "Import Klaar" at column "Type & Status"

        #Step 4 Check the status of "Soctar file import"
        Given Click on "soctar-1000055345-541447014114423407.csv" link
        Then Form header is "Social tariff batch details"
        And "Type" field value is "Import"
        And  "Status" field value is "DONE"

        #Step 5. Check the status of Social tariff quote
        When "1st" list element has cell value "541442938381222400" at column "EAN-code" within 450 seconds
        Then  "1st" list element has cell value "Quote Created" at column "Status" within 450 seconds
        And   "1st" list element has cell value "Contractnummer & start- en einddatum" at column "Contractnummer & start- en einddatum"




