# Design Architetturale

## Architettura package

Papyrus è organizzato in un'architettura modulare, in cui ogni elemento principale è rappresentato da un package dedicato. 



## Scelte di progettazione generali

Papyrus è stato progettato come una libreria modulare scritta in Scala, con un forte orientamento alla **programmazione funzionale** e alla **gestione immutabile dei dati**. L’approccio adottato si fonda su una costruzione del documento tramite **funzioni pure** e **builder specializzati**, che espongono un DSL leggibile, fortemente tipizzato e organizzato gerarchicamente.

L’utente interagisce con Papyrus invocando funzioni come `papyrus`, `metadata`, `content`, `title`, `text`, ecc., ognuna delle quali crea un contesto specifico utilizzando parametri impliciti (`using`) e builder composabili. Queste funzioni creano progressivamente la struttura interna del documento, fino al salvataggio finale del file HTML, CSS o PDF.

Ogni elemento del documento (es. titolo, paragrafo, lista, immagine) è rappresentato da un **oggetto di dominio** dotato di metodi `render` e `renderStyle`, che restituiscono rispettivamente la componente HTML e CSS, in formato stringa.

---

## Entità principali

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


---

## Modellazione del contesto

Papyrus impone **vincoli gerarchici** nella composizione del documento: ad esempio, una `SubSection` può essere dichiarata solo all’interno di una `Section`, così come un `item` può esistere solo all’interno di una `list`.

Questi vincoli sono **codificati staticamente a livello di tipo** tramite l’uso di **parametri impliciti (`using`)**. Ad esempio, la funzione:

```scala
def listing(init: ListBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder | ListBuilder): Unit = ...
```

I vincoli sono garantiti interamente a **compile-time**, riducendo la possibilità di errori strutturali.

---

## Flusso di costruzione e rendering

1. L’utente scrive il documento utilizzando le keyword offerte dal DSL Papyrus.
2. Ogni chiamata attiva un builder, che elabora e memorizza le informazioni relative all’elemento.
3. I builder vengono composti ricorsivamente, tenendo traccia del contesto e dello stile.
4. Al termine del processo, ciascun elemento produce:
    - HTML tramite un metodo di rendering dedicato;
    - CSS tramite una funzione di serializzazione dello stile.
5. Il documento completo viene assemblato da un componente finale che concatena tutti i contenuti e stili in un file HTML e un file CSS.


---

## Output e generazione dei file

La generazione dell’output è centralizzata in un modulo che si occupa di:

- Aggregare i contenuti HTML e lo stile CSS;
- Salvare i file nella directory specificata, oppure, in mancanza, in una cartella temporanea eliminata successivamente;
- Convertire il file HTML in PDF (se richiesto), utilizzando librerie esterne specifiche;
- Aprire automaticamente il file generato con il programma predefinito dell’utente.

In ogni caso, il file HTML fa riferimento al foglio `style.css`, generato dinamicamente sulla base delle impostazioni raccolte nei builder.

---

