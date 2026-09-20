@ConsumerSuite @DataDriven
Feature: Consumer Data-Driven Validation and Cross-Step State Sharing

  Scenario: Load JSON test data and transfer dynamic state via ScenarioContext
    Given I load the user profile "activeAdmin" from JSON test data
    When I store the active profile data in ScenarioContext
    Then I verify the profile data retrieved from ScenarioContext matches the JSON source
