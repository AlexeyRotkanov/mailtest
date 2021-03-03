Feature: User is able to send a draft mail

  Background:
    Given I opened the Mail site
    And I logged in with login distest@mail.ru and password P@ssword!23
    And I started to create a new mail
    And I filled in mail fields with default data
    And I saved a mail as draft
    And I opened "Draft" folder
    And I send draft mail

  Scenario: Draft mail is deleted from "Draft" folder
    When I opened "Draft" folder
    Then Mail was deleted from folder

  Scenario: Sent mail is saved in "Sent" folder
    When I opened "Sent" folder
    Then Mail is in folder