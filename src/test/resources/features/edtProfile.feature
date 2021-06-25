Feature: Edit profile

  This feature file contains several scenarios for the use case 'Edit Profile'

  Scenario: Edit username
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I enter 'TestUsername' in field with id 'username'
    And I click button with id 'save'
    Then I get a success message 'Profile successfully updated' displayed
    And I get redirected to 'Profile'

  Scenario: Already existing username
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I enter 'ExistingUsername' in field with id 'username'
    And I click button with id 'save'
    Then I get an error message 'This username already exists. Please enter different username' displayed
    And I get back to 'Edit Profile' page

  Scenario: Edit Email
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I enter 'test@domain.tld' in field with id 'email'
    And I click button with id 'save'
    Then I get a success message 'Profile successfully updated' displayed
    And I get redirected to 'Profile'

  Scenario: Invalid Email
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I enter 'testTest' in field with id 'email'
    And I click button with id 'save'
    Then I get an error message 'Please, enter a correct email address' displayed
    And I get back to 'Edit Profile' page

  Scenario: Edit Password
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I enter 'TestPassword' in field with id 'password'
    And I click button with id 'save'
    Then I get a success message 'Profile successfully updated' displayed
    And I get redirected to 'Profile'

  Scenario: Add profile picture
    Given I am registered user
    And I am logged in
    When I navigate to 'Profile'
    And I select 'Edit'
    And I click 'Add profile Picture'
    And I choose a picture to upload
    And I click button with id 'save'
    Then I get a success message 'Profile successfully updated' displayed
    And I get redirected to 'Profile'