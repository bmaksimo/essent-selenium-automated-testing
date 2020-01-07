@DWP
@B2B
@REGRESSION
@BUSINESS-DESK
@ALL
@Unstable
Feature: NUAT-553: Create Lead And Convert Lead - nl_BE

  Background:
    Given I logged in to DWP as "businessdesk.testautomation.b2b@essent.be"

  @NUAT-553
  Scenario: NUAT-553: Create Lead And Convert Lead
    And Left menu is "sales-marketing"
    And Top menu item is "Leads"
    And Add lead
    And New lead is
      | companyName    | firstName | secondName |
      | ESSENT BELGIUM | Levi      | Nine       |
    And "Geslacht" selection is "Onbekend"
    And "Telefoon" input is "+32 78 15 79 79"
    And "Mobiel" input is "+32 498 12 34 56"
    And "E-mailadres" input is "test@test.be"
    And Option "Bel me niet?" "is" "On"
    And Company VAT number is random
    And "Ondernemingsnummer" input is "parameter:VAT"
    And "Rechtsvorm" selection is "bvba"
    And Select Nace-Code
    And Sleep for 10 seconds
    And NaceCode in search is 01120 - Teelt van rijst
    And Company address is
      | street | houseNr | houseNrAdd | bus | postalCode | city    | country |
      | Random | 1       |            |     | 2550       | Kontich |         |

    When Customer details are confirmed
    And Plus action and "Converteer lead" of first customer from list
    And Changes are confirmed
    And Dashboard menu is "Details"
    And Customer type is "Prospect"

    Then There is one billing customer
