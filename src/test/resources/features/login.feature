Feature: User Login API

  Scenario: Successful login with valid credentials
    Given the user has the email "tom_marvolo@example.com" and password "Voldemort"
    When the user sends a login request
    Then the response status should be 200
    And the response should contain the email "tom_marvolo@example.com"

  Scenario: Unsuccessful login with incorrect password
    Given the user has the email "tom_marvolo@example.com" and password "wrong_password"
    When the user sends a login request
    Then the response status should be 422
    And the response should contain the error message "Wrong email/password combination"

  @UI
  Scenario Outline: User attempts to log in
    Given the user navigates to the login page
    When the user enters email "<email>" and password "<password>"
    And clicks the login button
    Then the result should be "<result>"

    Examples:
      | email                     | password  | result                              |
      | tom_marvolo@example.com   | Voldemort | success                             |
      | tom_marvolo@example.com   | wrongPass | Wrong email/password combination   |