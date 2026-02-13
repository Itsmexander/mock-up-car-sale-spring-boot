Feature: User Management
  As an application user
  I want to register and manage my account
  So that I can use the car sales platform

  Scenario: Successfully register a new user
    Given I have a valid user with firstname "Jane" and email "jane@example.com"
    When I register the user
    Then the response status should be 201

  Scenario: Fail to register a user with empty firstname
    Given I have a user with empty firstname
    When I register the user
    Then the response status should be 400

  Scenario: Get all users should not expose passwords
    Given a user with email "test@example.com" exists in the database
    When I get all users
    Then the response status should be 200
    And no user should have a password field
