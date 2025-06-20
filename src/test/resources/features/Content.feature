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

  Scenario: Render a section and subSection with title and text
    Given I add a section with title "titolo section" and text "testo section"
    And I add a subsection with title "titolo subsection" and text "testo subsection"
    When I render the document
    Then The HTML output should contain:
      """
      |<section>
      |  <h1>titolo section</h1>
      |  <span class="cls-3EA">testo section</span>
      |<section>
      |  <h2>titolo subsection</h2>
      |  <span class="cls-3EB">testo subsection</span>
      |</section>
      |</section>
      """

  Scenario: Render a document with a list
    Given I create a Papyrus document
    And I add a list with items:
      | Item 1 |
      | Item 2 |
      | Item 3 |
    When I render the document
    Then The HTML output should contain:
      """
      |<ul>
      |<li>Item 1</li>
      |<li>Item 2</li>
      |<li>Item 3</li>
      |</ul>
      """

  Scenario: Render a document with an image
    Given I create a Papyrus document
    And I add an image with source "src/test/resources/image/Pastore-tedesco.png" and caption "dog"
    When I render the document
    Then The HTML output should contain:
      """
      |<figcaption>dog</figcaption>
      """

  Scenario: Render a document with a table
    Given I create a Papyrus document
    And I add a table with rows:
      | Name  | Age |
      | Alice | 30  |
      | Bob   | 25  |
    When I render the document
    Then The HTML output should contain:
      """
      |<table>
      |
      |<tbody>
      |<tr>
      |<td colspan='1' rowspan='1'>Name</td>
      |<td colspan='1' rowspan='1'>Age</td>
      |</tr>
      |<tr>
      |<td colspan='1' rowspan='1'>Alice</td>
      |<td colspan='1' rowspan='1'>30</td>
      |</tr>
      |<tr>
      |<td colspan='1' rowspan='1'>Bob</td>
      |<td colspan='1' rowspan='1'>25</td>
      |</tr>
      |</tbody>
      |</table>
      """

