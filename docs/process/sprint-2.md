# Sprint 2

## Obiettivi

Avere una versione base del linguaggio che permetta di generare un PDF o HTML con i seguenti elementi:
- Title
- Text
- Content
- Metadata

## Deadline

31 maggio 2025  
Durata: 1 settimana

## Review

Gli obiettivi prefissati sono stati raggiunti, ottenendo un primo prototipo funzionante del linguaggio capace di generare sia HTML che PDF. Il lavoro ha richiesto più tempo del previsto, a causa di alcune complessità emerse in fase di sviluppo, non preventivate in fase di analisi.

## Analisi retrospettiva

Siamo soddisfatti dei risultati raggiunti, in particolare per aver iniziato concretamente lo sviluppo della libreria sulla base dell'analisi svolta nello Sprint 1. I diagrammi precedenti hanno guidato l'implementazione dei trait e della struttura del linguaggio.

Durante lo sprint sono emerse attività aggiuntive non previste, come la creazione di un file CSS separato e la definizione di tipi raffinati per la validazione dei dati tramite Iron. La gestione del carico di lavoro è risultata più impegnativa del previsto, e si è deciso di rivedere la pianificazione degli sprint futuri per migliorarne l’equilibrio.

| Task           | Descrizione                                   | Svolto da  | Durata |
|----------------|-----------------------------------------------| ---------- |--------|
| Sviluppo       | Definizione dei tipi con Iron                 | Cantagallo | 5h 30m |
| Sviluppo, Test | Implementazione Content                       | Capannini  | 1h     |
| Sviluppo, Test | Implementazione Text                          | Capannini  | 2h     |
| Sviluppo, Test | Implementazione Title                         | Capannini  | 1h     |
| Sviluppo, Test | Rifattorissazione object                      | Capannini  | 30m    |
| Sviluppo       | Implementazione Metadata                      | Capannini  | 3h 30m |
| Fix            | Risolta Implementazione Style                 | Cantagallo | 3h     |
| Fix            | Risolta implemetazione Text                   | Cantagallo | 2h     |
| Fix            | Risolta implementazione Metadata e Title      | Cantagallo | 1h     |
| Fix            | Risolta HTML e Css collegati                  | Cantagallo | 30m    |
| Fix            | Risolto primo DSL e Text di default con given | Cantagallo | 30m    |
| Analisi        | DSL                                           | Capannini  | 2h     |
| Sviluppo       | Elaborazione idee per style                   | Capannini  | 2h     |
| Sviluppo       | Elaborazione Dsl                              | Capannini  | 3h 30m |
| Sviluppo       | Elaborazione Dsl title                        | Capannini  | 2h 30m |
| Sviluppo       | TextDSL per risoluzione keyword inline        | Cantagallo | 2h     |
| Sviluppo       | Elaborazione DSL text                         | Cantagallo | 1h     |
| Fix            | Risoluzioni bug CSS                           | Cantagallo | 1h     |
| Sviluppo       | Risolta chaining DSL con TextDSL              | Cantagallo | 3h     |
| Sviluppo       | Builders                                      | Cantagallo | 1h     |
| Sviluppo       | Creazione pdf e refactory metadata            | Capannini  | 3h     |
| Test           | Test metadata                                 | Capannini  | 2h 30m |
| Sviluppo       | Output pdf e gestione estensione file         | Cantagallo | 1h     |
| Sviluppo       | Metadata con TextDSL                          | Cantagallo | 1h     |
