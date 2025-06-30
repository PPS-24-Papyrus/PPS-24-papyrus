## Glossario tecnico

### Keyword DSL

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

### Keyword inline
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

