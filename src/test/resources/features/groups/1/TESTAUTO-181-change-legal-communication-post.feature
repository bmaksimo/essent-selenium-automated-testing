@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: Create new customer with general communication preference: By email and update "Mandate"

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"
    @TESTAUTO-181
    Scenario: Change LEGAL communication preference from EMAIL on POST
        #1 - GUI contract creation
        When Plus menu is "Sales -> TK1 -> Creëer nieuwe offerte B2C"
        Then Form header is "Quote details"

        When "Tariefdatum" date is "now"
        And "Sales kanaal" selection is "Inbound"
        And Quote details are confirmed
        Then Form header is "Personal details"

        When Customer is random
        And Customer address is
                    | street           | houseNr | houseNrAdd | bus | postalCode | city    | country |
                    | Mechelsesteenweg | 2       |            |     | 2550       | Kontich |         |

        And Customer details are confirmed
        Then Form header is "Select package & fuel type"
        And "Pakket" selection is "Vast"
        And Checkbox "Gas Fix B2C (TC1)" is Unchecked
        And Package and Fuel Type is confirmed
        Then Form header is "Connection details"

        And EAN code is generated
        When "Startdatum" date is "35 days before now"
        And "EAN-code" input is "parameter:EAN-code-generated"
        And Option "test" "is" "On"
        And Connection details are confirmed
        Then Form header is "Billing details"

        When "Betalingswijze" selection is "Overschrijving"
        And Billing details are confirmed
        Then Form header is "Quote overview"

        When Option "Heeft de klant al getekend?" "is" "On"
        And "Kanaal ondertekening" selection is "Papier"
        And Quote is signed in "Kontich"
        And "Datum ondertekening" date is "now"
        And Quote is confirmed
        Then "1st" list element has cell value "Sales Getekend - Geaccepteerd" at column "Type & status"

        When Dashboard menu is "Details"
        And Plus action of "Legal" element from "Communicatievoorkeuren" and click on "Update"
        And E-mailadres input is cleared
        And "Kanaal" selection is "Per post"
        And click on "Opslaan"
        Then Message "Communication preferences for Billing customers switched from EMAIL to POST." is shown        ***********
        And "Algemeen" (General) preference has value "Per post"
        And "Mandaat" preference has value "Per post"
        And "Legal" preference has value "Per post"
        And Go to "Primary" contact persons and check if there isn’t an emailaddress

