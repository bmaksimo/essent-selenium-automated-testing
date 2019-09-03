@DWP
@B2C
@REGRESSION

Feature: NSTA 331- Product Change for TK1 type

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-331
    Scenario: Product Change TK1 type
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
        And "1st" List element with value at column "EAN-code" is checked
        Then "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds

        #2 - Start product change
        When Plus action of "1" element from "ContractsOnAccount" and click on "Productwijziging"
        And Tariff card has value of 1st item from list
        And "Pakket" selection is "Online"
        And Option "test" "is" "On"
        And Option "MM should respond?" "is" "On"
        And "Kanaal ondertekening" selection is "Online"
        Then Changes are confirmed
        And Bevestigen

        #3 - Confirm Product Change
        When Dashboard menu is "Sales"
        And Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        And "Datum ondertekening" date is "now"
        Then Changes are confirmed
        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        Then Changes are confirmed

        #4 - Check correctness of product change
         #4.1 - Check Contractlines
        When Dashboard menu is "Contracten"
        And "1st" list element has cell value "Wacht op startdatum" at column "Contractnummer" polling 120 seconds
        And Table "Actieve en toekomstige connecties" contains value "ONLINE" at column "EAN-code"
        And Table "Actieve en toekomstige connecties" contains value "Actief" at column "Contractnummer"
        And Product Change dates are "parameter:contractStartDate" and "parameter:contractEndDate"
        #check start date is same as from step 2 and end date is 1 day before start date
        And Start Date "parameter:contractStartDate" is "365 or 366" day bigger than End Date "parameter:contractEndDate"

         #4.2 - Check Discounts
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 60 seconds
        And Product Change dates are "parameter:contractStartDate" and "parameter:contractEndDate"

         #4.3 - Check Interactions
        When Dashboard menu is "Service"
        Then Table "Interacties" contains value "Confirmation product change" at column "Type & Onderwerp"
        And Click on link in View List at "1st" row and "Nummer & Communicatiekanaal" column polling 60 seconds
        And Check product change has succeeded

         #4.4 - Check Orders
        When Dashboard menu is "Contracten"
        And Click on link in View List at "1st" row and "EAN-code" column polling 60 seconds
        And Table "Afrekeningsfacturen" has matching value "RUNNING" at column "Status & Triggered plan" polling 15 seconds
        And Product Change dates are "parameter:contractStartDate" and "parameter:contractEndDate"



