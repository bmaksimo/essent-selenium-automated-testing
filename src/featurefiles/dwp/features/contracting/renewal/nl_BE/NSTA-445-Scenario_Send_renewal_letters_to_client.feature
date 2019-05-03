@DWP
@UNSTABLE
Feature: NSTA-445 Passive renewal of contract TK1 - with communication through letter

    Background:
        Given I logged in to DWP as "contracting.testautomation.b2c@essent.be"

    @NSTA-445-With_letter
    Scenario: Sign in to default electricity product
        When Plus menu is "Contracting -> TK1 Hernieuwingen -> Hernieuwingsbatches"
        Then View list header is "TK1 - Hernieuwingsbatches" appears within 20 seconds
        When Click on "parameter:suitecrm-customer-name" link
        And  Click on "VERSTUUR PASSIEVE HERNIEUWINGSBRIEVEN" link
        And Modal dialog is "Send passive renewal letter"
        And Modal dialogue is confirmed
        Then "Status batch" field value is "LETTERS_SENT"
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "verstuurd" at column "Status hernieuwing"
        And Table "Geselecteerde contractlijn voor hernieuwingsbatch" has matching value "Passief hernieuwd" at column "Offerte & status hernieuwing"
