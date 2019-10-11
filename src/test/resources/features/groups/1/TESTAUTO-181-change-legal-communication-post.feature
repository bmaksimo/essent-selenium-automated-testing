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
        And Click on Plus action of table "CommunicationPreferencesOnAccount" at row where "COMMUNICATIETYPE" is "Legal" and click on "Update"
        And E-mailadres input is cleared
        And "Kanaal" selection is "Per post"
        And Wait for 5 seconds

        And Click on "OPSLAAN" link
        Then Message "Communication preferences for Billing customers switched from EMAIL to POST." is shown
        And "Algemeen" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And "Mandaat" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And "Legal" preference at column "COMMUNICATIETYPE" is "Per post" on table "CommunicationPreferencesOnAccount"
        And Table "ContactpersonsOnAccount" does not contain any value at column "E-mail & Mijn essent" within 5 seconds
