# Requisiti

## Requisti di business

**Papyrus** è una libreria sviluppata in Scala che consente la generazione di documenti testuali in formato **HTML** o **PDF** tramite un DSL dedicato. Il progetto è concepito per offrire un sistema semplice, leggibile e modulare che permetta all’utente di comporre documenti utilizzando keyword strutturate, anche senza una conoscenza approfondita del linguaggio Scala.

Il DSL Papyrus permette di creare contenuti strutturati come titoli, testi, sezioni, sottosezioni, elenchi puntati o numerati, immagini, tabelle e metadati. È inoltre possibile personalizzare aspetto e stile del documento attraverso keyword specifiche.

Vengono effettuate **verifiche semantiche e sintattiche a compile-time**, garantendo coerenza nell’uso delle keyword e validità dei valori inseriti. Il linguaggio impone anche **vincoli contestuali**, come ad esempio la presenza obbligatoria di una `subsection` all’interno di una `section`.


## Requisiti funzionali

- Scrittura di documenti tramite keyword Papyrus, con struttura gerarchica e leggibile.
- Gestione dei metadati: nome file, lingua, estensione, margini, autore...
- Creazione di contenuti testuali e strutturati:
    - Sezioni, sottosezioni, testi e titoli;
    - Tabelle con supporto a due modalità costruttive;
    - Elenchi puntati.
- Personalizzazione dello stile:
    - Attributi come colore, allineamento, margine e dimensione;
    - Applicazione automatica di uno stile di default in assenza di personalizzazioni.
- Costruzione del documento tramite **builders** che operano in base al contesto.
- Generazione automatica di file HTML o PDF.


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
- Si assume che l’utente abbia accesso in lettura e scrittura al file system locale, nella directory in cui vengono generati HTML e PDF.
- Non è previsto un sistema di assistenza alla scrittura come suggerimenti automatici, validatori esterni o interfacce grafiche: l’utente scrive il documento interamente tramite codice.
- L’utente deve conoscere la struttura logica del DSL Papyrus: l’ordine e la gerarchia delle keyword sono fondamentali. Papyrus segnala errori solo nel caso in cui le regole vengano violate.
- Le keyword disponibili sono attualmente in lingua inglese. È necessaria una comprensione minima della terminologia tecnica per poter comporre documenti validi.

## Possibili estensioni

- Integrazione futura con interfaccia CLI o GUI.
- Supporto multilingua per la definizione delle keyword, basato sulla lingua impostata nei metadati.
- Introduzione di temi o template stilistici pronti all’uso.
