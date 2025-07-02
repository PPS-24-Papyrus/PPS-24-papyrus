# Capannini Daniel

Nel progetto di sono occupato della generazione di alcuni elementie delle rispettive keyword e builder relativi per il DSL e la definizione dei test Gherkin.
Nello sviluppo di alucini di questi componenti e' stato necessario l'utilizzo di tipi opachi e generici per garantire la correttezza semantica del codice.

Le parti principali di cui mi sono occupato sono:

- Table
- Image
- Gherkin test
- CI/CD


## Table 

La tabella è una delle strutture che si possono inserire nei documenti che ho progettato e implementato.
Permette di costruire tabelle conconteneti un generico tipo `T` e permette di avere celle di dimensioni variabili a piacimento dell'utente.


### Controllo forma tabella

Per garantire la correttezza della struttura della tabella, ho implementato un controllo che verifica che tutte le righe abbiano lo stesso numero di colonne, tenendo conto dei colspan. Questo controllo è realizzato tramite Prolog, che consente di esprimere vincoli semantici in modo chiaro e conciso.

```scala
    private def validateColspanConsistency(rows: List[Row[_]]): Either[String, Unit] =
      val data = rows.map(_.cells.map(_.colspan))
      val termString = data.map(_.mkString("[", ",", "]")).mkString("[", ",", "]")
      val prologTerm = Term.createTerm(termString)

      val goal = new Struct("validate_colspan_consistency", prologTerm)
      val result = engine(goal).headOption
    
      result match
        case Some(_) => Right(())
        case None =>
          val colCounts = data.zipWithIndex.map { case (row, i) => (i, row.sum) }
          val details = colCounts.map((i, c) => s"Row $i → $c columns").mkString("<br>")
          Left(s"<div style='color:red'><strong>Table structure error:</strong><br>$details</div>")
```

Per implementare questo controllo, ho utilizzato Prolog per calcolare la somma dei colspan di ogni riga e verificare che siano tutti uguali. Questo approccio consente di esprimere vincoli semantici in modo chiaro e conciso.

```prolog
   sum_list([], 0).
   sum_list([H|T], Sum) :- sum_list(T, Rest), Sum is H + Rest.
  
   row_colspans([], []).
   row_colspans([Row|Rest], [Sum|Sums]) :- sum_list(Row, Sum), row_colspans(Rest, Sums).
  
   all_equal([]).
   all_equal([_]).
   all_equal([X, X | Rest]) :- all_equal([X | Rest]).
  
   validate_colspan_consistency(Rows) :-
     row_colspans(Rows, Sums),
     all_equal(Sums).
```

### DSL e builder

Nel DSL, ho definito la keyword `table` e il relativo `TableBuilder` e `TableBuilderProxy`, che consente di costruire tabelle con celle di tipo generico `T`. Il `TableBuilderProxy` permette la costruzione di un `TableBuilder` immutabile. All'interno del builder ogni volta che si vuole aggiornare un valore che non sia l'aggiunta di `Row` si effettua un controllo, tramite il metodo `setOnce`, che controlla che non siano già stati aggiornati in precedenza permettendo cosi che i metodi possano essere chiamati al massimo una volta. Il builder permette di specificare le righe e le colonne della tabella, gestendo anche i `colspan` e `rowspan` per le celle. Essendo un tipo generico, il builder può essere utilizzato con qualsiasi tipo di dato, garantendo la flessibilità necessaria per diverse applicazioni ma per avere la miglior resa possibile gli andrebbe specificata che funzione utilizzare per renderizzare `T => String`.
Si è cercato di rendere il più facile e intuitivo la costrizione della tabella, come riportato negli esempi seguenti.

```scala
    table:
      "cella 1" | "cella 2"  | "cella 3"  | "cella 4"
      "cella 5" | "cella 6"  | "cella 7"  | "cella 8"
      "cella 9" | "cella 10" | "cella 11" | "cella 12"
      captionTable:
        "Table Caption"

    table:
      "prova2"  | "prova3" | "prova4" |  "prova5"
      "prova4" ^| "prova5" | "prova6" |^ "prova7"
      "prova6"  | "prova7"
      captionTable:
        "tabella con rowspan"
```

## Image

Le immagini sono un altro elemento che ho progettato e implementato. Permette di inserire immagini nei documenti, specificando il percorso del file e alcune caratteristiche grafiche come dimensione e allineamento se necessario.

### Controllo del percorso dell'immagine

Per garantire che il percorso dell'immagine sia valido e che il file esista, ho implementato un controllo che verifica l'esistenza del file e il formato corretto (solo .png e .jpg sono ammessi). Questo controllo è realizzato tramite un metodo che restituisce un `Either` per gestire gli errori in modo elegante.

```scala
private def checkPathImage(pathStr: String): Either[String, String] =
   val file = File(pathStr)

   Either.cond(
     file.isRegularFile,
     file,
     s"File not found or invalid: $pathStr"
   ).flatMap: f =>
     val name = f.name.toLowerCase
     if name.endsWith(".png") || name.endsWith(".jpg") then
       println(f.pathAsString.replace('\\', '/'))
       Right(f.pathAsString.replace('\\', '/'))
     else
       Left(s"Invalid format: only .png or .jpg allowed ($pathStr)")
```

### DSL e builder

Nella definizione del DSL, ho creato la keyword `image` e il relativo `ImageBuilder`, che consente di specificare il percorso dell'immagine e le opzioni grafiche.

```scala
  image:
    "src/test/resources/image/Pastore-tedesco.png" captionImage "A German Shepherd" alternative "A beautiful dog"

  image:
    "src/test/resources/image/Pastore-tedesco.png" width 300 alignment "left"
```

## Gherkin Test
I test Gherkin sono stati implementati per garantire la correttezza delle strutture e delle sue funzionalità. Ho definito una serie di scenari che coprono le principali funzionalità del progetto, come la creazione di tabelle, l'inserimento di immagini e la generazione di documenti.

### Esempio di test Gherkin

```gherkin
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
```