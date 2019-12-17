@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING
@Ignore

Feature: TESTAUTO-206 Dunning-HB1-flow-started

    Background:
        Given I login as API user "soapui_b2c"
    @TESTAUTO-206
    Scenario: Invoice block from Odoo
        And Create active B2C contract with metering "Off" and sign date "35 days before now"

       Given I logged in to DWP as "salesmarketing.testautomation.b2c@essent.be"
       When Left menu is "sales-marketing"
       And Top menu item is "Klanten"
       And Billing run "RECURRING" is triggered with process date "1 month from now"
       And Top action is Filter from "sales-marketing" menu retrying 5 times
       And "Klantnummer" input is "parameter:accountNumber"
       And Click on "parameter:accountNumber" link

       When Dashboard menu is "Contracten"
       And  "1st" list element has cell value "Actief" at column "Contractnummer" polling 550 seconds
       When Dashboard menu is "Billing"
       And Table "Transacties" contains value "Invoice (ADVANCE)" at column "ID & Type" retrying 10 times
       And Save invoice number

       Given I renew login to Odoo as "role_essent_ccm_user"
       And Odoo top menu is "Accounting"
       And Odoo left menu is "Dunning Instances"
       And Advanced search is
          |     field      |   operator  |          value          |
          | Customer       |   contains  | parameter:accountNumber |
       And Column "Customer" of the "1st" row is clicked
       And Dunning Instance Description is "HB1"
       And Dunning Instance State is "Done"
       And Dunning Instance Cost Entry is filled in
       And Dunning Instance Letter State is "Success"
