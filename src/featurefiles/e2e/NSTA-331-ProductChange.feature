@DWP
@B2C
@REGRESSION
@UNSTABLE
Feature: NSTA 331- Product Change for TK1 type

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NSTA-331
    Scenario: Product Change TK1 type

        # 1 - GUI Creation of active contract TK1 type
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
            | street          | houseNr | houseNrAdd |  bus | postalCode | city     | country |
            | Mechelsesteenweg| 2       |            |      | 2550       | Kontich  |         |
        And Customer details are confirmed
        Then Form header is "Select package & fuel type"

        When Package is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Kortingen is "50_part"
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        When "Startdatum" date is "2 weeks before now"
        And Electricity EAN code is "random"
        And Electricity market mock test is Open
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" is On
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then View list header is "Offertes"
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Contracten"
        Then View list header is "Actieve en toekomstige connecties"
        And "1st" List element with value at column "EAN-code" is checked
#        And "1st" list element has cell value "Actief" at column "Contractnummer" polling 450 seconds

        #2 - Start product change
        When Plus action of "1" element from "ContractsOnAccount" and click on "Productwijziging"
        And Tariefkaart is 1st item from list
#        And "Tariefkaart" selection is "TC_02_2019_B2C"
        When "Pakket" selection is "Online"
        When Option "test" is On
        When Option "MM should respond?" is On
        When "Kanaal ondertekening" selection is "Online"
        Then Changes are confirmed
        Then Bevestigen

        #3 - Confirm Product Change
        When Dashboard menu is "Sales"
        When Plus action of "1" element from "QuotesOnAccount" and click on "Handtekening ontvangen"
        When "Datum ondertekening" date is "now"
        Then Changes are confirmed
        When Plus action of "1" element from "QuotesOnAccount" and click on "Bevestig"
        Then Changes are confirmed

        #4 - Check correctness of product change

         #4.1 - Check Contractlines
        When Dashboard menu is "Contracten"
        Then Get Start Date
        Then Save End Date from active contract
        And "1st" list element has cell value "Wacht op startdatum" at column "Contractnummer" polling 60 seconds
        Then Table "Actieve en toekomstige connecties" contains value "ONLINE" at column "EAN-code"
        Then Table "Actieve en toekomstige connecties" contains value "Actief" at column "Contractnummer"
        Then Product Change dates are "parameter:startDate" and "parameter:EndDate-active-contract"
        #check start date is same as from step 2 and end date is 1 day before start date
        Then Start Date "parameter:startDate" is "365|366" day bigger than End Date "parameter:EndDate-active-contract"

         #4.2 - Check Discounts
        And Click on link in View List at "1st" row and "Nummer & Aanmaakdatum" column polling 20 seconds
        Then Product Change dates are "parameter:startDate" and "parameter:EndDate-active-contract"

         #4.3 - Check Interactions
        When Dashboard menu is "Service"
        Then Table "Interacties" contains value "Confirmation product change" at column "Type & Onderwerp"
        And Click on link in View List at "1st" row and "Nummer & Communicatiekanaal" column polling 20 seconds
        Then Check is product change "1 succeeded"

         #4.4 - Check Orders
        When Dashboard menu is "Contracten"
        And Click on link in View List at "1st" row and "EAN-code" column polling 20 seconds
        Then Table "Afrekeningsfacturen" contains value "RUNNING" at column "Status & Triggered plan"
        Then Product Change dates are "parameter:startDate" and "parameter:EndDate-active-contract"



