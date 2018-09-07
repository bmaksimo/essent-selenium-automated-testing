@B2B_REGRESSION
Feature: Dwp for handling task for canceling - nl_BE

    Background:
        Given I logged in to DWP as businessdesk.testautomation.b2b@essent.be

    Scenario:
        When Left menu is Werkbakken
        And Top action is Filters
        Then "Status" selection is "Open"

#        When Click on link in "Alle taken" View List at 1st row and "Plus Action" column
#    446e4fbc-8746-c9f7-e071-5b90cc622983
#    8586fd93-c107-8fd2-1d1d-5b922e674133
#        //tbody[@id='rows']/tr[1]/td[4]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5[.='J_29 UP_Elec_Floating_MMR_ToBeTakenOver 0831 152103']
        When Plus action of first customer from list
