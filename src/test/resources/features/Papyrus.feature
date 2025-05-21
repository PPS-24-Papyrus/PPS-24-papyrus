Feature: Papyrus DSL rendering

  Scenario: Render an empty Papyrus document
    Given I create an empty Papyrus document
    When I render the document
    Then The result should be an empty HTML structure

  Scenario: Render a document with only plain text
    Given I create a Papyrus document with the text "Questo è il testo del mio documento con il meta e style di default"
    When I render the document
    Then The result should an HTML structure contain "<body><p>Questo è il testo del mio documento con il meta e style di default</p></body>"

  Scenario:  Render an empty Papyrus document with metadata
    Given I create a Papyrus document with metadata
    When I render the document
    Then The result should an HTML structure contain "<head><title>Prova</title></head><body></body>"

  Scenario: Render a document with metadata and text
    Given I create a Papyrus document with metadata and the text "Questo è il testo del mio documento con il meta e style di default"
    When I render the document
    Then The result should an HTML structure contain "<head><title>Prova</title></head><body><p>Questo è il testo del mio documento con il meta e style di default</p></body>"

  Scenario: Render a PDF document with metadata and text
    Given I create a Papyrus document with metadata and the text "Questo è il testo del mio documento con il meta e style di default"
    When I render the document as PDF
    Then The result should be a PDF file with the title "Prova" and the text "Questo è il testo del mio documento con il meta e style di default"

  Scenario: Prevent setting content block more than once
    Given I create a new Papyrus document
    And I define a content block with title "Prova"
    When I try to define another content block with title "Seconda prova"
    Then The system should raise an error