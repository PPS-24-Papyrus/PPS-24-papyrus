Feature: Papyrus document metadata configuration

  Scenario: generate a correct default metadata
    Given I create a Metadata empty
    When I render the Metadata
    Then The result should be an default Metadata structure

  Scenario: Apply basic metadata
    Given I create a new Metadata
    When I set the Metadata:
      | fileName     | Prova        |
      | output-type  | html         |
      | author       | dany         |
    Then The metadata should contain:
      | fileName     | Prova        |
      | output-type  | html         |
      | author       | dany         |

  Scenario: Apply a style metadata
    Given I create a new Metadata
    When I set the Metadata:
      | fileName     | Prova        |
      | output-type  | html         |
      | author       | dany         |
      | color        | blue         |
      | margin       | 10           |
      | font-size    | 12           |
      | font-family  | Arial        |
    Then The metadata should contain:
      | fileName     | Prova        |
      | output-type  | html         |
      | author       | dany         |
      | color        | blue         |
      | margin       | 10           |
      | font-size    | 12           |
      | font-family  | Arial        |

  Scenario: Prevent setting metadata block more than once
    Given I create a new Papyrus document
    And I set the metadata:
      | author     | dany      |
    When I try to set the metadata again:
      | author     | marco     |
    Then The system should raise an error

  Scenario: Prevent setting content block more than once
    Given I create a new Papyrus document
    And I define a content block with title "Prova"
    When I try to define another content block with title "Seconda prova"
    Then The system should raise an error

  Scenario: Prevent setting font-family more than once
    Given I create a new Papyrus document
    And I set the metadata:
      | font-family  | Arial     |
    When I set the metadata again:
      | font-family  | Helvetica |
    Then The system should raise an error