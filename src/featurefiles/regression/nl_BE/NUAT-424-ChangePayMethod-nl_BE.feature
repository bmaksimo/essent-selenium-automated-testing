@DWP
@B2B
@REGRESSION
@CREDIT-AND-CONTROL
Feature: NUAT-424: Change Pay Method - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Klanten
        And Top action is Filters
        And "B2C/B2B" selection is "B2B"
        And Label input for "Type klant" is "CUSTOMER"
        And Click on link in View List at 1st row and "Klantnummer & Naam" column
        And Dashboard menu is Details
        Then View list header is "Billing customer"

        When Click on link in "Billing customer" View List at 1st row and "Plus Action" column
        And Row actions "Update" is clicked
        Then Modal "Update billing customer" is displayed

        When Payment method is switched
        And IBAN is NL43ABNA0978459932 if not empty
        And Payment details are confirmed
        Then Payment method is updated
        
        When Dashboard menu is Documenten
        Then Check if document "customer-signature.pdf" is present
