@REGRESSION
@DWP
@ODOO
@B2C
@ALL
@DUNNING

Feature: TESTAUTO-206 Dunning-HB1-flow-started

    Background:
        Given I renew login to Odoo as "role_essent_ccm_user"
    @TESTAUTO-206
    Scenario: Invoice block from Odoo
       And Odoo top menu is "Accounting"
       And Odoo left menu is "Dunning Instances"
       And Advanced search is
          |     field      |   operator  |          value          |
          | Customer       |   contains  | 1000000810 |
       And Column "Customer" of the "1st" row is clicked
       And Dunning Instance Description is "HB1"
       And Dunning Instance Cost Entry is filled in
       And Dunning Instance Letter State is "Success"
       And Dunning Instance State is "Done"
