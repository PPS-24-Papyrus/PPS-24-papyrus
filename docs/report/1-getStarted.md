# Introduzione

## Obiettivo del progetto
L'obiettivo del progetto Papyrus è la realizzazione di una libreria Scala per la generazione di documenti nei formati PDF e HTML, attraverso un DSL interno chiaro ed espressivo. La libreria consente di comporre documenti complessi in modo semplice e modulare, includendo tutti gli elementi comunemente utilizzati: titoli, paragrafi, liste, tabelle, immagini ed elementi grafici. La generazione dei file si basa sull’integrazione con librerie esterne come ITextRenderer (per la produzione di PDF da HTML), permettendo una gestione automatica del rendering a partire dalla definizione del contenuto.

## Stile funzionale e approccio estensibile
Una delle sfide principali affrontate nello sviluppo di Papyrus è stata la scelta di adottare uno stile funzionale e immutabile, sfruttando appieno le potenzialità offerte dal linguaggio Scala. Sono stati impiegati costrutti funzionali come Option, Either, parametri using/given, conversion implicite, e una struttura progettata per essere facilmente estensibile. Questo approccio ha permesso di minimizzare i side-effect e di costruire una base solida su cui poter estendere il DSL con nuovi elementi o comportamenti, mantenendo al tempo stesso un’interfaccia semplice per l’utente finale. L’obiettivo principale è infatti semplificare la creazione di documenti complessi, garantendo al contempo flessibilità e qualità del risultato.

## Get Started
Per iniziare a utilizzare Papyrus in un nuovo progetto SBT, è necessario aggiungere manualmente la libreria. Dopo aver generato il progetto, si consiglia di scaricare il JAR della versione più recente della libreria e inserirlo nella cartella lib del progetto, che può essere creata se non già presente.

Papyrus è progettata per essere facilmente integrabile in qualsiasi progetto Scala, offrendo un DSL interno per definire in modo rapido e leggibile documenti da esportare in formato PDF o HTML.

Una volta aggiunta la libreria, è possibile iniziare a scrivere documenti strutturati utilizzando classi e oggetti Scala, sfruttando un linguaggio dichiarativo pensato per descrivere elementi come titoli, paragrafi, immagini, tabelle, liste ed elementi grafici.

### Esempio di utilizzo

```scala
