@regression
Feature: User SignUp API Verification

  @API @smoke
  Scenario: Registering a new user successfully
    Given I generate unique user details
    When I send a request to register the user
    Then the response status should be 201

  @API
  Scenario: Registering an already existing user
    Given a user with email "test_user@example.com", username "Test User", and password "test_password"
    When I send a request to register the user
    Then the response status should be 422
    And the error message should be "Email already exists.. try logging in"

  @API
  Scenario: Registering a user with an invalid email
    Given a user with email "wrong_email", username "Test User", and password "test_password"
    When I send a request to register the user
    Then the response status should be 422
    And the error message should be "Email already exists.. try logging in"