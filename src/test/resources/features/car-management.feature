Feature: Car Management
  As a car dealer
  I want to manage my car inventory
  So that I can track cars for sale

  Scenario: Successfully register a new car
    Given I have a valid car with name "Toyota Camry" and price 25000 and year 2023
    When I register the car
    Then the response status should be 201

  Scenario: Fail to register a car with empty name
    Given I have a car with empty name
    When I register the car
    Then the response status should be 400

  Scenario: Search for cars
    Given a car with name "Honda Civic" exists in the database
    When I search for cars with query "Honda"
    Then the response status should be 200

  Scenario: Get car by ID - found
    Given a car with name "Test Car" exists in the database
    When I get the car by its ID
    Then the response status should be 200
    And the car name should be "Test Car"

  Scenario: Get car by ID - not found
    When I get a car with ID 99999
    Then the response status should be 404

  Scenario: Delete a car
    Given a car with name "Delete Me" exists in the database
    When I delete the car
    Then the response status should be 204
