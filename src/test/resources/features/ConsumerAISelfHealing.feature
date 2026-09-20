@ConsumerSuite @SelfHealing
Feature: AI-Powered Locator Self-Healing in Consumer Project

  Scenario: Consumer test auto-recovers broken element locators via AI Engine
    Given I acquire a consumer user with role "admin"
    When I navigate to the application portal
    When the AI pre-flight agent inspects and heals broken elements on the Login page
    Then the broken username locator should be automatically healed by the AI agent
    And I login using the AI-healed elements
    Then I should see the dashboard loaded successfully
