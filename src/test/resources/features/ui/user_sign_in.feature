Feature: User Sign in UI


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