@regression
Feature: Profile User


@UI @smoke
Scenario: Updating the user profile
Given I am a registered user with username "Test User", email "test_user@example.com", and password "test_password"
When I log in and update my profile bio to "new bio"
Then my profile bio should be updated to "new bio"