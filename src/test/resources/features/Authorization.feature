Feature: User is able to log in and log out

  Background:
    Given I opened the Mail site

  Scenario Outline: User is able to log in
    When I logged in with login <login> and password <password>
    Then I am logged in

    Examples:
      | login           | password    |
      | distest@mail.ru | P@ssword!23 |

  Scenario Outline: User is able to log out
    Given I logged in with login <login> and password <password>
    When I logged out
    Then I am logged out

    Examples:
      | login           | password    |
      | distest@mail.ru | P@ssword!23 |