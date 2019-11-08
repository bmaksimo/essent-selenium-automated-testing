@REGRESSION
@DWP
@B2C
@ALL
@Unstable

Feature: NSTA-329 Deduplication activated customer
    Background:
        Given I login as API user "soapui_b2c"
    @NSTA-329
    Scenario: From de-duplication of client
        And "Create_Quote" flow is started
        And Data is prepared for Create quote request for "prospect" and meter open is "On" and sign date is "35 days before now"
        And New tc1_quote is created
        And Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"
        And Simulation that customer signature is received
        And Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"
        And File is uploaded as scanned signature
        And Signin is confirmed
        And Contract is created
        And Contracted EAN exists on account
        And Payment details are received
        And Wait until contract instance starts
        And Check order in jbilling
        And I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
        And Left menu is "sales-marketing"


        When Click on top menu button PLUS and navigate to "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        And Form header is "Quote details"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        And Form header is "Personal details"
        And Customer is duplicated
        And Customer address is
            | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
            | Mechelsesteenweg | 2       |            |     | 2550       |         |         |
        And Deduplication dialogue "Soortgelijke klanten" is shown
        And Deduplication dialogue link "Create quote for account" is clicked
        And Save changes
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        And Field "Street" input is "Mechelsesteenweg"
        And Field "Housenumber" input is "2"
        And Field "Postalcode" input is "2550"
        And Field "City" input is "Kontich"
        And EAN code is generated
        And "EAN-code" input is "parameter:EAN-code-generated"
        And "Startdatum" date is "2 months from now"
        And Connection details are confirmed
        And Form header is "Billing details"
        And "Betalingswijze" selection is "Overschrijving"
        And  Billing details are confirmed
        And  Form header is "Quote overview"
        And Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And "Datum ondertekening" date is "now"
        And Getekend document is uploaded
        And "Plaats ondertekening" input is "Kontich"
        And Quote is confirmed
        And Dashboard menu is "Contracten"


        Then Two contracts are displayed
        And Dashboard menu is "Details"
        And There is one billing customer
