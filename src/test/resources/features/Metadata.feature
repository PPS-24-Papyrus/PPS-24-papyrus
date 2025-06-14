Feature: Papyrus document metadata configuration

  Scenario: generate a correct default metadata
    Given I create a new Metadata
    When I render the Metadata
    Then The result should be an default Metadata structure:
      """
      |<meta name="title" content="My Elegant Document"></meta>
      |<meta name="author" content="Author Name"></meta>
      |<meta name="charset" content="utf-8"></meta>
      |<link rel="stylesheet" href="style.css"></link>
      """

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

  Scenario: Apply a style
    Given I create a style text
    When I set the style text:
      | font-family  | Arial        |
      | font-size    | 12px         |
      | color        | #000000      |
    Then The style text should contain:
      | font-family  | Arial        |
      | font-size    | 12px         |
      | color        | #000000      |

