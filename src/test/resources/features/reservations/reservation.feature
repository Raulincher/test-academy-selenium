Feature:Check for a flight

  Narrative:
  In order to check flights
  As the host of iberia web
  I want to be able to check if a flight exists

  Scenario: Check flight existence
    Given I'm in the search page
    When I check the following flight:
      |Type | Origin  |  Destiny  |    Date    | ReturnDate   |  Adults  |   Children   |  Babies  |   TypeOfFlight   |
      |VUELO| Madrid  | Barcelona | 01/06/2025 | 10/06/2024   |    1     |      0       |     0    |     Solo ida     |
    Then I verify that the flight exists
