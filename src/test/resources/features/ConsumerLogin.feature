@ConsumerSuite @Smoke
Feature: Consumer Application Login and Portal Access

  Scenario: Consumer test successfully logs into portal using Core SDK
    Given I acquire a consumer user with role "admin"
    When I navigate to the application portal
    And the AI pre-flight agent validates consumer page elements
    And I perform login using consumer credentials
    Then I should see the dashboard loaded successfully
