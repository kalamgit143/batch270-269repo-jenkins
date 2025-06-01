Feature: cricbuzz E2E test for given test data in sentences

  @smoke
  Scenario Outline: Total runs value testing in Data-driven approach
    #UI
    Given I open browser
    When I launch cricbuz site for <matchid> and "<matchdetails>"
    #API
    And I connect to cricbuzz API with <matchid> matchid
    #E2E
    Then I compare the total of <innings_number> innings in the response body and in the scorecard page for equality
    #UI
    And I close browser

    Examples: 
      | matchid | matchdetails                                        | innings_number |
      |  100312 | ind-vs-eng-1st-odi-england-tour-of-india-2025       |              1 |
      |  112479 | zim-vs-ire-1st-odi-ireland-tour-of-zimbabwe-2025    |              2 |
      |   91805 | aus-vs-ind-4th-test-india-tour-of-australia-2024-25 |              4 |

  Scenario Outline: Runs value testing in Data-driven approach
    #UI
    Given I open browser
    When I launch cricbuz site for <matchid> and "<matchdetails>"
    And get runs done by "<batsman_name>" in <innings_number> innings from scorecard page
    #API
    And I connect to cricbuzz API with <matchid> matchid
    And get runs done by "<batsman_name>" in <innings_number> innings from JSON response body
    #E2E
    Then I compare the runs in the response body and in the scorecard page for equality
    #UI
    And I close browser

    Examples: 
      | matchid | matchdetails                                        | innings_number | batsman_name  |
      |  100312 | ind-vs-eng-1st-odi-england-tour-of-india-2025       |              2 | Shubman Gill  |
      |  112479 | zim-vs-ire-1st-odi-ireland-tour-of-zimbabwe-2025    |              1 | Brian Bennett |
      |   91805 | aus-vs-ind-4th-test-india-tour-of-australia-2024-25 |              4 | Virat Kohli   |
