@B2B_REGRESSION
Feature: Create a case with complaint

    Background:
        Given  I logged in to DWP as businessdesk.testautomation.b2b@essent.be
        When Left menu is Sales-marketing
        And  Top menu item is Klanten
        Then View list header is "Klanten" appears within 25 seconds


        When Top action is Filters
        And "Klantnummer" input is "150638828"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds

    Scenario:
        When Plus menu is "Service -> Case aanmaken voor de klant"
        And New case for account is created
        And View list header is "Cases" appears within 20 seconds
        And Click on link in View List at 1st row and "Nummer & Aanmaakdatum" column polling 20 seconds
        Then Case details are visible

    Scenario:
        When Dashboard menu is Service
        And "CASE TOEVOEGEN" is clicked
        And New case for account is created
        And View list header is "Cases" appears within 20 seconds
        And Click on link in View List at 1st row and "Nummer & Aanmaakdatum" column polling 20 seconds
        And Case details are visible
