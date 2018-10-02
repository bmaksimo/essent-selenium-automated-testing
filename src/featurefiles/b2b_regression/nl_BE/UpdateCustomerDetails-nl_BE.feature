@B2B_REGRESSION
Feature: Update customer details - dunning stop

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds


        When Top action is Filters
        And "Klantnummer" input is "150638828"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Bijwerken details klant"
        #And Activate dunning stop
        And Activate "Aanmaningsstop"
        Then Change is immediately visible in Finance & Legal section
        And Plus menu is "Service -> Wijzigingen klant -> Bijwerken details klant"
        And clik on chechbox
        #Aanmaningsstop
