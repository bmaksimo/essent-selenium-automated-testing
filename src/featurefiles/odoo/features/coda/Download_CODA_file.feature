@ODOO
Feature: Odoo download of CODA file

    Background:
        Given I renew login to Odoo as "role_essent_ccm_user"

    @DEV-CODA-DOWNLOAD
    Scenario: Download CODA file
        When Cleanup Odoo CODA files
        And Odoo top menu is "Accounting"
        And Odoo left menu is "Customers"
        And Odoo filter is "parameter:account-nr"
        And Column "Account Number" with value "parameter:account-nr" is clicked
        And Button "Journal Items" is clicked
        And Generate CODA in the first row with "Amount receivable" is clicked

        Then Modal title contains "Download CODA"
        And Generated CODA file is downloaded
        And Modal button "Close" is clicked
