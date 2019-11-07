@ALL
@E2E
@DWP
@REGRESSION
@SMOKE-TESTS
Feature: Initial set of tests

    #Check is Login working with all iWelcome users for automation purposes
    Scenario: Login with user "salesmarketing.testautomation.b2c@essent.be" in DWP
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"

    Scenario: Login with user "businessdesk.testautomation.b2b@essent.be" in DWP
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    Scenario: Login with user "billing.testautomation@essent.be" in DWP
        Given I logged in to DWP as "billing.testautomation@essent.be"

    Scenario: Login with user "contracting.testautomation.b2c@essent.be" in DWP
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    Scenario: Login with user "billing_testautomation" in JBilling
        Given I logged in to JBilling as "billing_testautomation"

    Scenario: Login with user "role_essent_ccm_user" in Odoo
        Given  I logged in to Odoo as "role_essent_ccm_user"

    Scenario: Login with api b2b user "soapui_b2b"
        Given I login as API user "soapui_b2b"


    #Check is tariff sheet available

    #Check is contract created through API (B2C TK1) + Invoice creation
    Scenario: Contract creation B2C TK1 API
        Given I login as API user "soapui_b2c"
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

        Given I renew login to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        When Dashboard menu is "Billing"
        Then Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 5 times

    #Check is contract created through API (B2B UP/TK2)
    Scenario: Contract creation B2B UP API
    And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"

    #Check is contract created through UI (B2B TK1)
    Scenario: Contract creation B2B TK1 UI
        Given  I logged in to DWP as "contracting.testautomation.b2c@essent.be"
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Nieuwe TK1 offerte (B2B) aanmaken"
        And Company name is random
        And "Bedrijfsnaam" input is "parameter:company-name"
        And Company VAT number is random
        And "Ondernemingsnummer" input is "parameter:VAT"
        And Value at "Klantacceptatie" in the card "Perform customer acceptance check" is "Geaccepteerd"
        And Customer acceptance checks page is confirmed
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Contact person"

        When "Rechtsvorm" selection is "bvba"
        And Select Nace-Code
        And NaceCode in search is 01120 - Teelt van rijst

        And Field "First name" input is "parameter:contact-person-first-name"
        And Field "Last name" input is "parameter:contact-person-last-name"

        And Company address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Random           | 1       |            |     | 2550       | Kontich |         |

        And Company contact info is generated
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2B (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Option "test" "is" "On"
        And EAN code is generated
        And "Startdatum" date on "Elektriciteit Vast" card is "now"
        And "EAN-code" input on "Elektriciteit Vast" card is "parameter:EAN-code-generated"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 500 seconds

    #Check is contract created through UI (B2C TK1)
    Scenario: Contract creation B2C TK1 UI
        Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "1 month before now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When Electricity EAN code is "random"
        And "Startdatum" date is "1 months before now"
        And "Type aansluiting" selection is "YMR"
        And "Meternummer" input is "1000"
        And Option "test" "is" "On"
        And Connection details are confirmed
        And Save changes
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        Then  Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Quote is signed
        And Quote is signed in "Kontich"
        Then Quote is confirmed




