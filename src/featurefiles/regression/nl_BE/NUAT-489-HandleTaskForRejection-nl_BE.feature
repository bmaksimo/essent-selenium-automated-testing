@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
Feature: NUAT-489: Handle Task For Rejection - nl_BE

    Background:
        Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

    @NUAT-489
    Scenario: Handle task for rejection
        When Left menu is "Werkbakken" waiting for 10 seconds
        And Top menu item is "Rejections" waiting for 10 seconds
        And Top action is "Filters" waiting for 30 seconds
        And "Status" selection is "Open" waiting for 30 seconds
        And Click on link in View List at "1st" row and "Klant & EAN-code" column waiting for 60 seconds
        When Dashboard menu is "Details" waiting for 60 seconds
        And Get Contract Number
        And Top arrow button is "Back" waiting for 5 seconds
        And Top arrow button is "Back" waiting for 5 seconds
        And Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list waiting for 40 seconds
        And Resolution input is "Mark as rejected for testing" waiting for 20 seconds
        Then Changes are confirmed waiting for 20 seconds

        When Left menu is "sales-marketing" waiting for 10 seconds
        And Top menu item is "Klanten" waiting for 10 seconds
        And Top action is "Filters" waiting for 30 seconds
        And "Klantnummer" input is "parameter:contractNumber"
        And Click on link in View List at "1st" row and "Klantnummer & Naam" column waiting for 60 seconds
        And Dashboard menu is "Service" waiting for 100 seconds
        Then Table "Taken" contains value "Marktberichten - Rejection" at column "Naam & Type & Subtype" waiting for 20 seconds
        And Table "Taken" contains value "Afgehandeld" at column "Status" waiting for 20 seconds
