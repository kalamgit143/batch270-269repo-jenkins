Feature: cricbuz E2E test for given test data in sentences

  Scenario: Total value testing using data table concept
    #UI
    Given I open browser
    #E2E
    When I launch cricbuz site and check equality of total runs with corresponding API given runs
      | 100312 | ind-vs-eng-1st-odi-england-tour-of-india-2025       | 1 |
      | 112479 | zim-vs-ire-1st-odi-ireland-tour-of-zimbabwe-2025    | 2 |
      |  91805 | aus-vs-ind-4th-test-india-tour-of-australia-2024-25 | 4 |
    #UI
    And I close browser

  Scenario Outline: Total value testing with cross browser using data table and examples concept
    #UI
    Given I open "<browser>" browser
    #E2E
    When I launch cricbuz site and connect to corresponding API to check equality of total runs
      | matchid | matchdetails                                        | innings_number |
      |  100312 | ind-vs-eng-1st-odi-england-tour-of-india-2025       |              1 |
      |  112479 | zim-vs-ire-1st-odi-ireland-tour-of-zimbabwe-2025    |              2 |
      |   91805 | aus-vs-ind-4th-test-india-tour-of-australia-2024-25 |              4 |
    #UI
    And I close browser

    @smoke
    Examples: 
      | browser |
      | chrome  |

    Examples: 
      | browser |
      | firefox |
      | edge    |
