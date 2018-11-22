@DWP
@BUSINESS-DESK
@REGRESSION
Feature: NUAT-441: Contract Take Over

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario: Contract take over
        
        When Left menu is sales-marketing
        Then Top menu item is Leads

        When Add lead
        And New lead is
            | companyName    | firstName | secondName | telephone       | mobile           | email        | gender |
            | ESSENT BELGIUM | Levi      | Nine       | +32 78 15 79 79 | +32 498 12 34 56 | test@test.be | Male |
        And Plus action and "Converteer lead" of first customer from list
        And Changes are confirmed
        And Get client number
        Then Go back to home screen

        When Left menu is sales-marketing
        Then Top menu item is Contracten
        And Top action is Filters
        And "Pricing group" selection is "UP"
        And "Contracttype" selection is "Overname"
        Then Click on link in View List at 1st row and "Bedrijfsnaam & Contactpersoon" column polling 20 seconds

        When Plus action of "1" element from "ContractsOnAccount" and click on "Overnamecontract"
        And Search by client number
        And Changes are confirmed
        And "Contract startdatum" date is "now"
        Then Changes are confirmed

        When Dashboard menu is Sales
        And Offertes plus options is "Verzenden naar klant"
        And "Mij een e-mail sturen" turn on
        And Changes are confirmed
        And Offertes plus options is "Handtekening ontvangen"
        And Getekend document is uploaded
        And "Taak aanmaken voor de manager" turn on
        And Changes are confirmed
        And Offertes plus options is "Bevestig"
        And Changes are confirmed
        Then Validate contract was "Overname" and "Handtekening ontvangen - Geaccepteerd"
