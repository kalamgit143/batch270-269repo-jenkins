@smoke
Feature: cricbuz E2E test for given test data in sentences

	Scenario: Title testing 
		#UI
		Given I open browser
		When I launch cricbuz site for 100312 and "ind-vs-eng-1st-odi-england-tour-of-india-2025"
		Then I should see "India vs Pakistan" in the title of scorecard page
		And I close browser
	Scenario: Total runs value testing, here test data in sentences as int and string types
    #UI
    Given I open browser
    When I launch cricbuz site for 100312 and "ind-vs-eng-1st-odi-england-tour-of-india-2025"
    #API
    And I connect to cricbuzz API with 100312 matchid
    #E2E
    Then I compare the total of 1 innings in the response body and in the scorecard page for equality
    #UI
    And I close browser

  Scenario: Specific player Runs value testing, here test data in sentences as int and string types
    #UI
    Given I open browser
    When I launch cricbuz site for 100312 and "ind-vs-eng-1st-odi-england-tour-of-india-2025"
    And get runs done by "Shubman Gill" in 2 innings from scorecard page
    #API
    And I connect to cricbuzz API with 100312 matchid
    And get runs done by "Shubman Gill" in 2 innings from JSON response body
    #E2E
    Then I compare the runs in the response body and in the scorecard page for equality
    #UI
    And I close browser

  Scenario: Specific player Runs value testing using test data as Docstring in sentences
    #UI
    Given I open browser
    When I launch cricbuz site for 100312 and "ind-vs-eng-1st-odi-england-tour-of-india-2025"
    And get runs scored from scorecard page related to
      """
      Shubman Gill,
      2 innings
      """
    #API
    And I connect to cricbuzz API with 100312 matchid
    And get runs done by "Shubman Gill" in 2 innings from JSON response body
    #E2E
    Then I compare the runs in the response body and in the scorecard page for equality
    #UI
    And I close browser
