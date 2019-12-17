@DWP
@SALES-MARKETING
@REGRESSION
@B2C
@API
@ALL
Feature: NSTA-327: Creating a B2C Quote TC1 with "Supplier Switch" without using Market Mock

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-327
    @SUPPLIER-SWITCH-NO-MM
    Scenario: Create a B2C Account with Quote, With supplier switch, without using Market Mock

        #Step 1. Selecteer type Offerte / Select Quote type
        And Create active B2C contract with metering "On" and sign date "35 days before now"

        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds

        #Step 9. Check market messages
        When Dashboard menu is "Marktberichten"
        Then Table "Marktberichten" contains value "START ACCESS Supplier Switch" at column "Module & Label"
        Then Table "Marktberichten" contains value "parameter:contractDate" at column "Status & ED"
        Then Table "Marktberichten" contains value "Gesloten" at column "Status & ED"

