@ODOO
Feature: Odoo download of CODA file
Background:
    Given I renew login to Odoo as "t.geets"

@DEV-CODA-DOWNLOAD
Scenario: Download CODA file
    When Cleanup Odoo CODA files
    And Odoo top menu is "Accounting"
    And Odoo left menu is "Customers"
    And Odoo filter is "1000098715"
    And Column "Account Number" with value "1000098715" is clicked
    And Button "Journal Items" is clicked
    And Generate CODA in the "1st" row is clicked

    Then Modal title contains "Download CODA"
    And Generated CODA file is downloaded
    And Modal button "Close" is clicked
