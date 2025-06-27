# Introduzione

## Obiettivo del progetto
L'obiettivo del progetto Papyrus è la realizzazione di una libreria Scala per la generazione di documenti testuali, attraverso un DSL interno chiaro ed espressivo. La libreria consente di comporre documenti complessi in modo semplice e modulare, includendo tutti gli elementi comunemente utilizzati come titoli, elenchi puntati, tabelle, immagini e sezioni.

## Stile funzionale e approccio estensibile
Una delle sfide principali affrontate nello sviluppo di Papyrus è stata la scelta di adottare uno stile funzionale e immutabile, sfruttando appieno le potenzialità offerte dal linguaggio Scala. Sono stati impiegati costrutti funzionali come Option, Either, parametri using/given, conversion implicite, e una struttura progettata per essere facilmente estensibile. Questo approccio ha permesso di minimizzare i side-effect e di costruire una base solida su cui poter estendere il DSL con nuovi elementi o comportamenti, mantenendo al tempo stesso un’interfaccia semplice per l’utente finale. L’obiettivo principale è infatti semplificare la creazione di documenti complessi, garantendo al contempo flessibilità e qualità del risultato.

