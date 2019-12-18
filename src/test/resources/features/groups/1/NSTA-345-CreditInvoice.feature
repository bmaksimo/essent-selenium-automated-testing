@REGRESSION
@DWP
@B2C
@API
@ALL
@NOREG04
@Unstable

Feature: NSTA-345:Credit Invoice

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-345
    Scenario: Credit Invoice
        # 1 - Create an active contract via API
        And Create active B2C contract with metering "Off" and sign date "35 days before now"

        # 2 - invoice run advance
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is Filter from "billing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"

        # Recalculate invoice
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Herbereken tussentijdse factuur"
        And New Amount Invoice is "300" for EAN "parameter:EAN-code"
        Then Invoice run is scheduled
        And Sleep for 10 seconds

        # 3 - Check if interactions are created for VKM and CNM
        When Dashboard menu is "Service"
        Then Table "Interacties" contains value "VKM" at column "Type & Onderwerp" retrying 60 times
        Then Table "Interacties" contains value "CNM" at column "Type & Onderwerp" retrying 60 times

        #Asserts
         # 1 - Check if Old invoice is credited (check if CNM is created for same amount as old VKM)
         # 2 - Check if there is a new invoice created for the amount you selected
        When Dashboard menu is "Billing"
        Then Credit invoice has same negative amount as advance invoice

        # 4 - Check if CNM has been sent to customer
        When Dashboard menu is "Service"
        And Click on "Nummer & Communicatiekanaal" matching value "CNM" at column "Type & Onderwerp"

        # 5 - Check Balance of new invoice credit -- currently not being tested as balance update can take too long to occur and this is momentarily an accepted behavior
        # when balance update is timeboxed this check needs to be uncommented
#        When Dashboard menu is "Billing"
#        Then Balance is the same as from the latest invoice
