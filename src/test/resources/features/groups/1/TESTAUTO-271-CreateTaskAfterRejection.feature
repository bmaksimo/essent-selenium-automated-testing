@REGRESSION
@DWP
@B2C
@ALL

Feature: TESTAUTO-271-Create task after rejection

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-271
    Scenario: create task after rejection
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        When B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link

        When Dashboard menu is "Contracten"
        And  "1st" List element with value at column "EAN-code" is checked
        Then  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        When Dashboard menu is "Marktberichten"
        When Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code"
        Then Select Contractline dialog is confirmed
        When "Module" selection is "INITIATE LEAVING CUSTOMER"
        And "Label" selection is "Without Handover Document"
        And Option "Testing?" "is" "On"
        And Select Contractline dialog is confirmed
        Then "1st" list element has cell value "INITIATE STOP ACCESS" at column "Module & Label" polling 450 seconds
        And Refresh "REFRESH MARKTBERICHTEN" till "Geweigerd" is visible in table
#        And Copy task number


        When Dashboard menu is "Service"
        Then Table "Taken" contains value "parameter: taskNumber" at column "Number & Start data"
        And Table "Taken" contains value "Handle rejection market_messaging - rejection" at column "Naam & Type & Subtype"



