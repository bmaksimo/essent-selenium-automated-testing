@REGRESSION
@DWP
@B2C
@API
@PERFORMANCE
@ALL
Feature: NSTA-345:Credit Invoice

    Background:
        Given I login as API user "soapui_b2c"

    @NSTA-345
    Scenario: Credit Invoice
        #Create an active contract via API
        And "Create_Quote" flow is started
        When Data is prepared for Create quote request for "prospect" and meter open is "Off" and sign date is "35 days before now"
        And New tc1_quote is created
        Then Quote status is "ACCEPTED"
        And Quoteline exists
        And Quoteline status is "Sent to customer"

        When Simulation that customer signature is received
        Then Quote stage status is "SIGNATURE RECEIVED"
        And Quoteline status is "Signature received"

        When File is uploaded as scanned signature
        Then Signin is confirmed
        And Contract is created
        And Contracted EAN exists on account

        When Payment details are received
        Then Wait until contract instance starts
        And Check order in jbilling

        #2 - invoice run advance
        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is Filter from "billing" menu retrying 5 times
        And "Klantnummer" input is "parameter:accountNumber"
        Given View List element "Id Billing customer & persoon/familie sleutel" is collected as parameter at "1st" list row
        When Billing run "RECURRING" is triggered with process date "1 month from now"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Billing"
        And "1st" List element with value at column "ID & Type" is checked

        #Recalculate invoice
        When Plus action of "1" element from "TransactionsOnAccount" and click on "Herbereken tussentijdse factuur"
        And New Amount Invoice is "300" for EAN "parameter:EAN-code"
        Then Invoice run is scheduled

        Given I renew login to DWP as "billing.testautomation@essent.be"
        When Left menu is "billing"
        And Top menu item is "Klanten"
        And Top action is "Filters"
        And "Klantnummer" input is "parameter:accountNumber"
        Given Click on link in View List at "1st" row and "Klantnummer & Naam" column polling 30 seconds
        When Dashboard menu is "Service"

          #3 - Check if interactions are created for VKM and CNM
        Then Table "Interacties" contains value "VKM" at column "Type & Onderwerp" within 600 seconds
        Then Table "Interacties" contains value "CNM" at column "Type & Onderwerp" within 600 seconds
        When Dashboard menu is "Billing"

        #Asserts
         #1 - Check if Old invoice is credited (check if CNM is created for same amount as old VKM)
         #2 - Check if there is a new invoice created for the amount you selected
        Then Credit invoice has same negative amount as advance invoice

         #4 - Check if CNM has been sent to customer
        When Dashboard menu is "Service"
        And Click on "Nummer & Communicatiekanaal" matching value "CNM" at column "Type & Onderwerp"
        Then Check product change has succeeded

        #5 - Check Balance of new invoice credit -- currently not being tested as balance update can take too long to occur and this is momentarily an accepted behavior
        # when balance update is timeboxed this check needs to be uncommented
#        When Dashboard menu is "Billing"
#        Then Balance is the same as from the latest invoice
