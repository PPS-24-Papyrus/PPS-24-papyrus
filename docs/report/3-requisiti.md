# Requisiti

## Requisti di business

**Papyrus** è una libreria sviluppata in Scala che consente la generazione di documenti testuali in formato **HTML** o **PDF** tramite un DSL dedicato. Il progetto è concepito per offrire un sistema semplice, leggibile e modulare che permetta all’utente di comporre documenti utilizzando keyword strutturate, anche senza una conoscenza approfondita del linguaggio Scala.

Il DSL Papyrus permette di creare contenuti strutturati come titoli, testi, sezioni, sottosezioni, elenchi puntati o numerati, immagini, tabelle e metadati. È inoltre possibile personalizzare aspetto e stile del documento attraverso keyword specifiche, come `color`, `fontSize`, `backgroundColor`, ecc.

Grazie all’impiego della libreria **Iron**, vengono effettuate **verifiche semantiche e sintattiche a compile-time**, garantendo coerenza nell’uso delle keyword e validità dei valori inseriti. Il linguaggio impone anche **vincoli contestuali**, come ad esempio la presenza obbligatoria di una `subsection` all’interno di una `section`, attraverso l’impiego di costrutti `using`.


## Requisiti funzionali

- Scrittura di documenti tramite keyword Papyrus, con struttura gerarchica e leggibile.
- Gestione dei metadati: nome file, lingua, estensione, margini, autore...
- Creazione di contenuti testuali e strutturati:
    - Sezioni, sottosezioni, testi e titoli;
    - Tabelle con supporto a due modalità costruttive;
    - Elenchi con supporto per numerarli, ordinarli e annidati.
- Personalizzazione dello stile:
    - Attributi come colore, allineamento, margine e dimensione;
    - Applicazione automatica di uno stile di default in assenza di personalizzazioni.
- Costruzione del documento tramite **builders** che operano in base al contesto (es. `SectionBuilder`, `ListBuilder`, ecc.).
- Generazione automatica di:
    - File HTML con contenuto strutturato;
    - File CSS dinamico (`style.css`) calcolato a partire dai parametri stilistici del DSL;
    - PDF finale a partire dall’HTML, tramite librerie esterne.

Ogni elemento è costruito come oggetto stilizzato, renderizzato poi in HTML e/o CSS. Lo stile viene serializzato separatamente nel foglio `style.css` e collegato automaticamente al file HTML.

## Requisiti non funzionali

- Verifiche a compile-time su keyword, valori e contesti logici.
- DSL compatibile con utenti non tecnici, grazie a una sintassi chiara ed espressiva.
- Architettura modulare, con componenti (es. liste, tabelle, immagini) separabili ed estendibili.
- Composizione text-only: assenza di interfaccia grafica o CLI.
- Processo efficiente di generazione ricorsiva dei documenti.
- Documentazione inline presente su tutte le funzioni pubbliche.
- Testing rigoroso orientato al comportamento (TDD + BDD).

## Assunzioni

- Papyrus viene utilizzato principalmente attraverso esecuzione da codice Scala, tramite `sbt`. L’uso da REPL o ambienti interattivi è tecnicamente possibile ma non attualmente prioritario.
- Si assume che l’utente abbia accesso in lettura e scrittura al file system locale, nella directory in cui vengono generati HTML, PDF e CSS.
- Non è previsto un sistema di assistenza alla scrittura come suggerimenti automatici, validatori esterni o interfacce grafiche: l’utente scrive il documento interamente tramite codice.
- L’utente deve conoscere la struttura logica del DSL Papyrus: l’ordine e la gerarchia delle keyword sono fondamentali. Papyrus segnala errori solo nel caso in cui le regole strutturali vengano violate.
- Le keyword disponibili sono attualmente in lingua inglese. È necessaria una comprensione minima della terminologia tecnica per poter comporre documenti validi.


## Implementazione

- Linguaggio: Scala 3.3.6
- Librerie utilizzate:
    - `"org.scalatest" %% "scalatest" % "3.2.18" % Test`
    - `"io.github.iltotore" %% "iron" % "2.4.0"`
    - `"com.openhtmltopdf" % "openhtmltopdf-pdfbox" % "1.0.10"`
    - `"org.xhtmlrenderer" % "flying-saucer-pdf" % "9.1.22"`
    - `"com.github.pathikrit" %% "better-files" % "3.9.2"`
    - `"io.cucumber" % "cucumber-scala_2.13" % "8.17.0" % Test`
    - `"io.cucumber" % "cucumber-junit" % "7.14.0" % Test`
    - `"junit" % "junit" % "4.13.2" % Test`
    - `"org.junit.vintage" % "junit-vintage-engine" % "5.9.3" % Test`

- Strumenti di supporto:
    - EBNF per la definizione formale della grammatica del DSL;
    - UML per la modellazione dell’architettura e dei flussi;
    - Microsoft Planner per la gestione degli sprint (metodologia SCRUM).

- Team:
    - **Luca Cantagallo** (Scrum Master, Developer)
    - **Daniel Capannini** (Product Owner, Developer)

## Requisiti opzionali

- Integrazione futura con interfaccia CLI o GUI.
- Supporto multilingua per la definizione delle keyword, basato sulla lingua impostata nei metadati.
- Introduzione di temi o template stilistici pronti all’uso.
