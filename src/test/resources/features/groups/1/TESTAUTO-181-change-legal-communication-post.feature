@REGRESSION
@DWP
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-181 - Create new customer with general communication preference: By email and update "Mandate"

    Background:
        Given I login as API user "soapui_b2c"
    @TESTAUTO-181
    Scenario: Change LEGAL communication preference from EMAIL on POST
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "On" and sign date is "35 days before now" with communication by email
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

        Given I renew login to DWP as "contracting.testautomation.b2c@essent.be"
        When Left menu is "sales-marketing"
        And Top menu item is "Klanten"
        And Top action is Filter from "sales-marketing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 60 seconds
        When Dashboard menu is "Details"
        And Click on Plus action of table "CommunicationPreferencesOnAccount" at row where "COMMUNICATIETYPE" is "Legal" and click on "Update"
        And "Legal" E-mailadres input is cleared
        And "Kanaal" selection is "Per post"
        And Wait for 5 seconds

        And Click on "OPSLAAN" link
        Then Message "Communication preferences for Billing customers switched from EMAIL to POST." is shown
        And "Algemeen" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And "Mandaat" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And "Legal" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And Table "ContactpersonsOnAccount" does not contain any value at column "E-mail & Mijn essent" within 5 seconds
