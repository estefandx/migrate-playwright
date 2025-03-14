@regression
Feature: Search by Tag


  @API @smoke
  Scenario Outline: API Verification for valid tags
    When the user sends a request to search articles with tag "<tag>"
    And the response should contain articles related to tag "<tag>"

    Examples:
      | tag     |
      | test    |
      | simple  |
      | cypress |

  @API
  Scenario: Search articles with an invalid tag
    When the user sends a request to search articles with tag "invalid_tag_name"
    And the response should contain zero articles
