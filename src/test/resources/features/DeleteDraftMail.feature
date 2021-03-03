Feature: User is able to delete a draft mail

  Background:
    Given I opened the Mail site
    And I logged in with login distest@mail.ru and password P@ssword!23
    And I started to create a new mail
    And I filled in mail fields with default data
    And I saved a mail as draft
    And I opened "Draft" folder

  Scenario: User is able to delete draft mail using Drag and Drop
    When I delete draft mail using drag'n'drop
    And I opened "Trash" folder
    Then Mail is in folder

  Scenario: User is able to delete draft mail using Drag and Drop
    When I delete draft mail using context menu
    And I opened "Trash" folder
    Then Mail is in folder