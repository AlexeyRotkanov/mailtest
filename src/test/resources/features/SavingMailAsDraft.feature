Feature: User is able to save a mail as draft

  Background:
    Given I opened the Mail site

  Scenario: User is able to save a mail as draft
    Given I logged in with login distest@mail.ru and password P@ssword!23
    And I started to create a new mail
    And I filled in mail fields with default data
    When I saved a mail as draft
    And I opened "Draft" folder
    Then Mail is in folder

  Scenario: Draft mail is saved with correct data
    Given I logged in with login distest@mail.ru and password P@ssword!23
    And I started to create a new mail
    And I filled in mail fields with default data
    And I saved a mail as draft
    When I opened "Draft" folder
    Then Saved draft mail contains correct data

