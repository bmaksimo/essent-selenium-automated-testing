@DWP
@BASIC
@CONTRACT
@_RENEWAL
@REGRESSION

Feature: Test the passive renewal of B2B contract.

    Background:
        Given   I logged in in DWP as BusinessDeskB2B

    Scenario:
        When Top Action is Plus Menu
        And Plus Menu is "Contracting -> UP-TK2 - Om contracten te hernieuwen"
        Then View List Header is "UP-TK2 - Om contracten te hernieuwen"

        #Selecting and submitting to renew contracts
        When Top Action is Filters
        #Valid values: Accepted,  Refused
        And  Filter element "Klantacceptatie" selection is "Accepted"
        #Valid value is '' - empty string or Default
        And  Filter element "Status contractlijn" selection is ""
        And  Filter element "Einddatum vanaf" date input is "$today + 3 months"
        And  Filter element "Einddatum tot" date input is "$today +  4 months"
        #Valid values are Sales Signed (Not marked), Sales Signed ()
        And  Select 2 List rows having cell value Sales Signed at column Type & Status (hernieuwing)
        ##No, the column names are wrong!
        #And  Store cell values of selected list rows at column "COMPANY NAME & CONTACT" as "selected_contracts"
        #And  List View action is "PASSIVE RENEW"

        #Phrase means "Rows, selected 3 steps back but column values have changed"
        #Check. Do we need additional check:
        # List contains rows with "selected_contracts" values at "COMPANY NAME & CONTACT"
        #Then Selected List rows have cell value "Sales Signed (Passive renewal (with communication))" at column "TYPE & (RENEW) STATUS"

        #Confirming to renew contracts
        #When  Top Action is Plus Menu
        #And   Plus Menu is "Contracting -> UP/TC2 - Passive renewal quotes"
        #Then  View List Header is "UP/TC2 - Passive Renewal quotes"
