Feature: Pausar activada

  Scenario: Pausar la actividad luego de minimizar la aplicacion
    Given I press view with id "start_button"
    And I wait for 3 seconds
    And I press the menu key
    And I go back
    And I press "Pause"
    Then I should see "Resultados"