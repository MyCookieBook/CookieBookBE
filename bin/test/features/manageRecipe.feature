Feature: Manage recipes

  This feature file contains several scenarios for the use case 'Manage recipes'

  Scenario: Create recipe
    Given I am logged in
    When I click button with id '+'
    And I enter a 'TestCategory' in field with id 'Category/Path'
    And I enter 'TestTitle' in field with id 'Recipe Title'
    And I enter 'TestAuthor' in field with id 'Recipe Author'
    And I choose '3 Cookies' in field with the id 'difficulty level'
    And I enter 'TestIngredients' in field with id 'Ingredients'
    And I enter 'TestSteps' in field with id 'Steps'
    And I tap on toolbar button 'Save'
    Then I see success message 'New recipe is created!'
    And I get redirected to 'Recipe' page

  Scenario: Edit recipe with valid data
    Given I am logged in
    When I navigate to 'Recipe List' page
    And I open a recipe
    And I select 'Edit' on a recipe
    And I enter a 'TestCategory' in field with id 'Category/Path'
    And I enter 'TestTitle' in field with id 'Recipe Title'
    And I enter 'TestAuthor' in field with id 'Recipe Author'
    And I enter 'TestIngredients' in field with id 'Ingredients'
    And I enter 'TestSteps' in field with id 'Steps'
    And I enter a number to input field with id 'Duration'
    And I choose '5 Cookies' in field with the id 'Difficulty Level'
    And I tap on toolbar button 'Save'
    Then I see success message 'Recipe successfully updated!'
    And I get redirected to 'Recipe' page

  Scenario: Delete recipe yes/no
    Given I am logged in
    When I navigate to 'Recipe List' page
    And I open a recipe
    And I select button with the id 'Delete'
    And I see confirmation popup 'Do you really want to delete this recipe?'
    And I click button with the id 'Yes'
    Then the recipe is deleted
    And I get redirected to 'Recipe List' page

