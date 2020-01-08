@REGRESSION
@NOREG04
@API
@DWP
@B2C
@ALL

Feature: TESTAUTO-271-Create task after rejection

    Background:
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    @TESTAUTO-271
    Scenario: create task after rejection
        And Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And B2C TC1 Contract uses "FAKE" address and switch type is "MOVE IN"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        And Click on "parameter:accountNumber" link
        And Dashboard menu is "Contracten"
        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
        And Dashboard menu is "Marktberichten"

        When Click on "START NIEUW MARKTBERICHT"
        And Click Select Contractline
        And Dialog search input is current "parameter:EAN-code"
        And Select Contractline dialog is confirmed
        And "Module" selection is "INITIATE LEAVING CUSTOMER"
        And "Label" selection is "Without Handover Document"
        And Option "Testing?" is "On"
        And Extern bericht is "ILC - zonder energieovernamedocument - Afgewezen"
        And Select Contractline dialog is confirmed
        And "1st" list element has cell value "INITIATE LEAVING CUSTOMER" at column "Module & Label" polling 450 seconds
        And "1st" list element has cell value "Geweigerd" at column "Status & ED" polling 550 seconds
        And Copy task number with modul "INITIATE LEAVING CUSTOMER" in list "MarketTransactionsOnAccount"
        And Dashboard menu is "Service"

        Then Table "Taken" contains value "parameter:taskNumber" at column "Number & Start date"
        And Table "Taken" contains value "Handle rejection market_messaging - rejection" at column "Naam & Type & Subtype"



