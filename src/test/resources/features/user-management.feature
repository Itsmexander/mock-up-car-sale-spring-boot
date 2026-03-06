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

  Scenario: Login with valid credentials
    Given a user with email "login@example.com" and password "password123" exists in the database
    When I login with email "login@example.com" and password "password123"
    Then the response status should be 200
    And the response should contain a token
    And the response should contain a userId

  Scenario: Login with unknown email
    When I login with email "nobody@example.com" and password "password123"
    Then the response status should be 401
    And the error message should be "User not found"

  Scenario: Login with wrong password
    Given a user with email "badpass@example.com" and password "correctpass1" exists in the database
    When I login with email "badpass@example.com" and password "wrongpassword1"
    Then the response status should be 401
    And the error message should be "Wrong password"

  Scenario: Change password successfully
    Given a user with email "changepw@example.com" and password "oldpass1" exists in the database
    When I change password for that user with old password "oldpass1" and new password "newpass1"
    Then the response status should be 200

  Scenario: Change password with wrong old password
    Given a user with email "wrongpw@example.com" and password "correct1" exists in the database
    When I change password for that user with old password "wrongoldpass1" and new password "newpass1"
    Then the response status should be 401
    And the error message should be "Wrong password"

  Scenario: Change password with non-existent user ID
    When I change password for user id 99999 with old password "any" and new password "any"
    Then the response status should be 401
    And the error message should be "User not found"
