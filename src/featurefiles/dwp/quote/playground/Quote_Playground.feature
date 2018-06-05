@_QUOTE
Feature: Creating a B2C Quote

  Background:
      Given I logged in as SERVICE_DESK_B2C on the DWP Main Page
      Given I optionally discard a previous flow:

  Scenario: We can create a B2C Quote
    When I start a B2C quote flow:
    #And I select Inbound sales channel and accept standard quote type for B2C:
