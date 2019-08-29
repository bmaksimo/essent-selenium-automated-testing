@DWP
@CORE
@CORE-MENU
Feature: DWP left-, top- and Plus- menu navigation

    Background:
        Given  I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    Scenario: Navigation menu -> view list -> view -> poll and click cell item  -> cockpit -> plus menu
        When Left menu is "sales-marketing"
        And  Top menu item is "Klanten"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 20 seconds
        And Plus menu is "Service -> Wijzigingen klant -> Bijwerken details klant"
