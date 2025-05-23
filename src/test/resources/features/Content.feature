Feature: Papyrus document content rendering

  Scenario: Render a document with title and paragraph
    Given I create a new Papyrus document
    And I add a title "Prova"
    And I add a paragraph "testo di prova"
    When I render the document
    Then The HTML output should contain:
      """
      <h1>Prova</h1>
      <p>testo di prova</p>
      """

  Scenario: Render a section with title and text
    Given I add a section with title "titolo section" and text "testo section"
    When I render the document
    Then The HTML output should contain:
      """
      <section>
        <h2>titolo section</h2>
        <p>testo section</p>
      </section>
      """

  Scenario: Render an image inside a section
    Given I add an image with:
      | caption | Image caption       |
      | path    | path/to/image.png   |
      | alt     | Image description   |
      | size    | 150x150             |
    When I render the document
    Then The HTML output should contain:
      """
      <figure>
        <img src="path/to/image.png" alt="Image description" width="150" height="150"/>
        <figcaption>Image caption</figcaption>
      </figure>
      """

  Scenario: Render a list of items
    Given I add a list with:
      | el 1 |
      | el 2 |
      | el 3 |
    When I render the document
    Then The HTML output should contain:
      """
      <ul>
        <li>el 1</li>
        <li>el 2</li>
        <li>el 3</li>
      </ul>
      """

  Scenario: Subsection outside of a section is not allowed
    Given I create a new Papyrus document
    And I add a subsection with title "Titolo Subsection" and text "Testo subsection"
    When I render the document
    Then The system should raise an error.

  Scenario: Table rendering inside a subsection
    Given I create a new Papyrus document
    And I add a section with title "Main Section"
    And I add a subsection with title "Tabular Data" and text "Esempio tabella"
    And I add a table with caption "Table caption", headers "Header 1, Header 2", and rows:
      | Row 1 Col 1 | Row 1 Col 2 |
      | Row 2 Col 1 | Row 2 Col 2 |
    When I render the document
    Then The HTML output should contain a correct table