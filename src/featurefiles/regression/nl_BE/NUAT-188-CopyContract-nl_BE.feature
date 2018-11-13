@REGRESSION
    Feature: Scenario for copy contract
        Background:
            Given I logged in to DWP as soapui_b2c

        Scenario: Copy Contract
            When Left menu is sales-marketing
            And Top menu item is Klanten
            And B2B Active Contract is "UP" product type and use "FAKE" address and switch type is "MOVE IN"
            And Top action is Filters
#            And "B2C/B2B" selection is "B2B"
#            And "Type klant" selection is "Klant"
            And "Klantnummer" input is "parameter:accountNumber"
            Then Click on link in View List at 1st row and "Klantnummer & Naam" column polling 20 seconds
            
            When Dashboard menu is Contracten
            And Plus action of "1" element from "ContractsOnAccount" and click on "Kopie contract"
            And "Testing" turn on
            And Input in Mystery switch is "Hijacked"

