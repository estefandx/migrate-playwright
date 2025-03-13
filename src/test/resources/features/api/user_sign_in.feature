Feature: User Sign in API


  @API
  Scenario: Successful login with valid credentials
    Given the user has the email "tom_marvolo@example.com" and password "Voldemort"
    When the user sends a login request
    Then the response status should be 200
    And the response should contain the email "tom_marvolo@example.com"

  @API
  Scenario: Unsuccessful login with incorrect password
    Given the user has the email "tom_marvolo@example.com" and password "wrong_password"
    When the user sends a login request
    Then the response status should be 422
    And the response should contain the error message "Wrong email/password combination"

