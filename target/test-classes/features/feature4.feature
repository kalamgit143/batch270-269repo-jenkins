Feature: cricbuz E2E test by taking test cases data from CSV or txt file

  Scenario: Total value testing by getting test data from a CSV file
    #UI
    Given I open browser
    #E2E
    When I launch cricbuz site and connect to API to check equality of runs scored by a batsman using data in a CSV or Text file
    #UI
    And I close browser
