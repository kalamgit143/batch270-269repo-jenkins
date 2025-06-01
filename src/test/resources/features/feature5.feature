Feature: cricbuz E2E test by taking test cases data from XLS or XLSX file

  Scenario Outline: Total value testing with cross browser by getting test data from a XLSX file and examples
    #UI
    Given I open "<browser>" browser
    #E2E
    When I launch cricbuz site in "<browser>" and corresponding API to check equality of total runs by using data in a XLSX file
    #UI
    And I close browser

    Examples: 
      | browser |
      | chrome  |
      | firefox |
      | edge    |
