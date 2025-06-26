# Design Architetturale
## Design

### Scelte di progettazione generali

Papyrus è stato progettato come una libreria modulare scritta in Scala, con un forte orientamento alla **programmazione funzionale** e alla **gestione immutabile dei dati**. L’approccio adottato si fonda su una costruzione del documento tramite **funzioni pure** e **builder specializzati**, che espongono un DSL leggibile, fortemente tipizzato e organizzato gerarchicamente.

L’utente interagisce con Papyrus invocando funzioni come `papyrus`, `metadata`, `content`, `title`, `text`, ecc., ognuna delle quali crea un contesto specifico utilizzando parametri impliciti (`using`) e builder composabili. Queste funzioni creano progressivamente la struttura interna del documento, fino al salvataggio finale del file HTML, CSS o PDF.

Ogni elemento del documento (es. titolo, paragrafo, lista, immagine) è rappresentato da un **oggetto di dominio** dotato di metodi `render` e `renderStyle`, che restituiscono rispettivamente la componente HTML e CSS, in formato stringa.

> <!-- Inserire UML generale delle entità principali della libreria -->

---

### Entità principali

Le entità centrali all’interno di Papyrus si suddividono in due categorie:

- **Builder (entità attive):**
    - `PapyrusBuilder`, `MetadataBuilder`, `ContentBuilder`, `SectionBuilder`, `SubSectionBuilder`, `ListBuilder`, `TitleBuilder`, ecc.
    - Ogni builder mantiene stato interno immutabile e contiene metodi `with*` per modificare configurazioni in maniera pura.
    - Al termine della configurazione, ciascun builder espone un metodo `build()` che restituisce l’oggetto finale (es. `Title`, `Section`, `Text`, ecc.).

- **Elementi (entità passive):**
    - `Title`, `Text`, `List`, `Table`, `Image`, `Paragraph`, ecc.
    - Ogni elemento rappresenta una componente del documento e implementa due metodi:
        - `render`: restituisce il corrispondente HTML;
        - `renderStyle`: restituisce il CSS associato allo stile.

Ogni builder è progettato per operare su uno specifico livello del documento e può essere composto in modo ricorsivo, mantenendo sempre **consapevolezza del contesto**.

> <!-- Inserire diagramma UML della gerarchia dei builder e dei render -->

---

### Modellazione del contesto

Papyrus impone **vincoli gerarchici** nella composizione del documento: ad esempio, una `SubSection` può essere dichiarata solo all’interno di una `Section`, così come un `item` può esistere solo all’interno di una `list`.

Questi vincoli sono **codificati staticamente a livello di tipo** tramite l’uso di **parametri impliciti (`using`)**. Ad esempio, la funzione:

```scala
def listing(init: ListBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder | ListBuilder): Unit = ...
```

I vincoli sono garantiti interamente a **compile-time**, riducendo la possibilità di errori strutturali.

---

### Flusso di costruzione e rendering

1. L’utente scrive il documento utilizzando le keyword offerte dal DSL Papyrus.
2. Ogni chiamata attiva un builder, che elabora e memorizza le informazioni relative all’elemento.
3. I builder vengono composti ricorsivamente, tenendo traccia del contesto e dello stile.
4. Al termine del processo, ciascun elemento produce:
    - HTML tramite un metodo di rendering dedicato;
    - CSS tramite una funzione di serializzazione dello stile.
5. Il documento completo viene assemblato da un componente finale che concatena tutti i contenuti e stili in un file HTML e un file CSS.

> <!-- Inserire diagramma del flusso di costruzione e rendering -->

---

### Output e generazione dei file

La generazione dell’output è centralizzata in un modulo che si occupa di:

- Aggregare i contenuti HTML e lo stile CSS;
- Salvare i file nella directory specificata, oppure, in mancanza, in una cartella temporanea eliminata successivamente;
- Convertire il file HTML in PDF (se richiesto), utilizzando librerie esterne specifiche;
- Aprire automaticamente il file generato con il programma predefinito dell’utente.

In ogni caso, il file HTML fa riferimento al foglio `style.css`, generato dinamicamente sulla base delle impostazioni raccolte nei builder.

---

### Glossario tecnico

#### Keyword DSL

| Keyword                | Descrizione                                                                                           |
|------------------------|--------------------------------------------------------------------------------------------------------|
| `papyrus`              | Punto d’ingresso del documento. Deve contenere esattamente un blocco `metadata` e uno `content`.      |
| `metadata`             | Blocco che definisce i metadati e lo stile globale del documento (titolo, autore, font, colore, ecc.).|
| `content`              | Contenuto principale del documento: sezioni, testo, immagini, tabelle, liste, ecc.                    |
| `section`              | Sezione del contenuto. Può contenere sottosezioni e altri elementi.                                   |
| `subsection`           | Sottosezione nidificata all’interno di una `section`.                                                  |
| `title`                | Titolo gerarchico. Il livello (H1/H2/H3) è determinato dal contesto (papyrus/content/section).         |
| `text`                 | Blocco testuale. Supporta stili inline mediante keyword come `fontWeight`, `color`, `textDecoration`.  |
| `bold`                 | Inserisce un `text` marcato come grassetto. Shortcut di `text` + `fontWeight("bold")`.                 |
| `italic`               | Inserisce un `text` in corsivo. Shortcut di `text` + `fontStyle("italic")`.                            |
| `underline`            | Inserisce un `text` sottolineato. Shortcut con `textDecoration("underline")`.                         |
| `listing`              | Crea una lista ordinata o puntata. Può essere annidata, supporta ordinamenti.                         |
| `item`                 | Elemento di una lista. Inseribile solo all’interno di un `listing`.                                    |
| `listType`             | Specifica il tipo di lista: `"ul"` (puntata) o `"ol"` (numerata).                                     |
| `ordered`              | Specifica il criterio di ordinamento: `"alphabetical"`, `"length"`, `"reverse"`, `"levenshtein"`.     |
| `reference`            | Valore stringa usato per ordinamento Levenshtein in un `listing`.                                     |
| `table[T]`             | Inserisce una tabella parametrica (`T`). Può contenere righe, caption, e configurazioni visive.        |
| `caption`              | Imposta la descrizione della tabella.                                                                 |
| `withList`             | Inserisce una lista di righe (`List[RowBuilder[T]]`) nella tabella.                                    |
| `backgroundColorTable`| Imposta il colore di sfondo della tabella.                                                             |
| `textAlignTable`       | Allineamento del testo (`"left"`, `"center"`, `"right"`, `"justify"`).                                |
| `marginTable`          | Margine esterno della tabella.                                                                         |
| `widthTable`           | Larghezza della tabella in pixel.                                                                      |
| `alignTable`           | Allineamento orizzontale della tabella (`"left"`, `"right"`, `"center"`).                             |
| `renderTable`          | Funzione di rendering delle celle: `(T => String)`.                                                    |
| `image`                | Inserisce un’immagine con attributi opzionali (`src`, `alt`, `caption`, `width`).                      |
| `nameFile`             | Imposta il nome del file finale da generare (`document.pdf`, ecc.).                                   |
| `extension`            | Estensione del file: `"pdf"` o `"html"`.                                                               |
| `path`                 | Percorso per il salvataggio del file.                                                                  |
| `language`             | Codice ISO della lingua del documento (`"it"`, `"en"`, `"de"`, ecc.).                                 |
| `metadataTitle`        | Titolo del documento nel metadata.                                                                     |
| `author`               | Nome dell’autore nel metadata.                                                                         |
| `charset`              | Charset del documento HTML (`"utf-8"`, `"iso-8859-1"`...).                                              |
| `styleSheet`           | Percorso del foglio di stile CSS utilizzato nel rendering HTML.                                        |
| `font`                 | Font del corpo del documento. Valore applicato nel builder `MainStyleBuilder`.                         |
| `fontSize`             | Dimensione del font globale. Intero compreso tra 8 e 72.                                                |
| `lineHeight`           | Altezza della riga, espressa come moltiplicatore (`1.0`–`3.0`).                                        |
| `textColor`            | Colore del testo globale (`"#000000"`, `rgb(...)`, ecc.).                                              |
| `backgroundColor`      | Colore di sfondo del documento.                                                                        |
| `textAlign`            | Allineamento del paragrafo: `"left"`, `"center"`, `"right"`, `"justify"`.                             |
| `margin`               | Margine esterno del body. Intero tra 0 e 200 pixel.                                                    |

| Proprietà Inline  | Tipo           | Contesto     | Descrizione                                                                 |
|-------------------|----------------|--------------|-----------------------------------------------------------------------------|
| `color`           | `ColorString`  | text         | Imposta il colore del testo (`#fff`, `rgb(...)`, `"red"`, ecc.).           |
| `fontWeight`      | `FontWeight`   | text         | Peso del font: `"normal"` o `"bold"`.                                      |
| `fontStyle`       | `FontStyle`    | text         | Stile del font: `"normal"` o `"italic"`.                                   |
| `textDecoration`  | `TextDecoration`| text        | Decorazione del testo: `"none"`, `"underline"`, `"overline"`.              |
| `newLine`         | `String`       | text         | Aggiunge una nuova riga con testo opzionale.                               |
| `level`           | `Level`        | title        | Livello del titolo (1–3). Determinato anche automaticamente dal contesto.  |
| `font`            | `FontFamily`   | title        | Font da utilizzare per il titolo (es. `"Helvetica"`).                      |
| `fontSize`        | `FontSize`     | title        | Dimensione del font del titolo (8–72).                                      |
| `textColor`       | `ColorString`  | title        | Colore del testo del titolo.                                                |
| `textAlign`       | `Alignment`    | title        | Allineamento: `"left"`, `"center"`, `"right"`, `"justify"`, ecc.           |



> <!-- Inserire eventuali diagrammi UML specifici, definizione EBNF o illustrazioni d’uso -->
