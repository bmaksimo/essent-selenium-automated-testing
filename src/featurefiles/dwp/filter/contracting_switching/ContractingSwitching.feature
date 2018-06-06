@REGRESSION
@DWP
@_FILTER
@BASIC
Feature: DWP UI: Filter elements

    Background:
        Given I logged in as ESSENT_ADMIN on the DWP Main Page
        Given I optionally discard a previous flow:

    Scenario: We see all Contracting Switching Main Menu Filter elements
        When I check filter elements defined for Left Menu item: 'CONTRACTING_SWITCHING'
            |Number|
            |Name|
            |Account name|
            |Account number|
    Scenario: We see all Contracting Switching -> 'CONTRACTING_SWITCHING' Filter elements
        When I click on the following Left Menu item: 'CONTRACTING_SWITCHING'
        Then I check filter elements defined for 'CONTRACTING_SWITCHING' Left Menu Item and ' MS_SEND_ILC' Top Menu Item
            |Account number|
            |Name|
            |EAN|
        #And  I check filter elements defined for 'CONTRACTING_SWITCHING' Left Menu Item and 'MOVE_SEND_ILC' Top Menu Item
        #And  I check filter elements defined for ...

