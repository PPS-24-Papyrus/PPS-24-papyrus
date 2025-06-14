Feature: Papyrus document content rendering

  Scenario: Render a document with title and paragraph
    Given I create a Papyrus document
    And I add a title "Prova"
    And I add a paragraph "testo di prova"
    When I render the document
    Then The HTML output should contain:
      """
      |<h1>Prova</h1>
      |<span class="cls-3E8">testo di prova</span>
      """

  Scenario: Render a section with title and text
    Given I add a section with title "titolo section" and text "testo section"
    When I render the document
    Then The HTML output should contain:
      """
      |<section>
      |  <h1>titolo section</h1>
      |  <span class="cls-3E9">testo section</span>
      |</section>
      """


  Scenario: Subsection outside of a section is not allowed
    Given I create a new Papyrus document
    And I add a subsection with title "Titolo Subsection" and text "Testo subsection"
    When I render the document
    Then The system should raise an error.

