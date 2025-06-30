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

## Registro attività

| Fase             | Descrizione dell'attività                          | Responsabile | Durata  |
|------------------|----------------------------------------------------|--------------|---------|
| Sviluppo         | Definizione dei tipi con vincoli tramite Iron      | Cantagallo   | 5h 30m  |
| Sviluppo, Test   | Implementazione del modulo `Content`               | Capannini    | 1h      |
| Sviluppo, Test   | Implementazione del componente `Text`              | Capannini    | 2h      |
| Sviluppo, Test   | Implementazione del componente `Title`             | Capannini    | 1h      |
| Sviluppo, Test   | Rifattorizzazione dell’oggetto principale          | Capannini    | 30m     |
| Sviluppo         | Implementazione del modulo `Metadata`              | Capannini    | 3h 30m  |
| Fix              | Correzione implementazione `Style`                 | Cantagallo   | 3h      |
| Fix              | Correzione implementazione `Text`                  | Cantagallo   | 2h      |
| Fix              | Correzione `Metadata` e `Title`                    | Cantagallo   | 1h      |
| Fix              | Sistemazione CSS e rendering HTML collegato        | Cantagallo   | 30m     |
| Fix              | Prima DSL e testo di default tramite `given`       | Cantagallo   | 30m     |
| Analisi          | Progettazione struttura DSL                        | Capannini    | 2h      |
| Sviluppo         | Ideazione e sketch del modulo `Style`              | Capannini    | 2h      |
| Sviluppo         | Progettazione e prototipo DSL                      | Capannini    | 3h 30m  |
| Sviluppo         | Definizione DSL per titoli                         | Capannini    | 2h 30m  |
| Sviluppo         | Introduzione di `TextDSL` per keyword inline       | Cantagallo   | 2h      |
| Sviluppo         | Progettazione DSL per testi                        | Cantagallo   | 1h      |
| Fix              | Risoluzione bug di rendering CSS                   | Cantagallo   | 1h      |
| Sviluppo         | Collego tra `TextDSL` e DSL principale             | Cantagallo   | 3h      |
| Sviluppo         | Implementazione dei `Builder`                      | Cantagallo   | 1h      |
| Sviluppo         | Generazione PDF e rifattorizzazione `Metadata`     | Capannini    | 3h      |
| Test             | Test approfonditi del modulo `Metadata`            | Capannini    | 2h 30m  |
| Sviluppo         | Gestione dell'estensione file e output PDF/HTML    | Cantagallo   | 1h      |
| Sviluppo         | Integrazione `Metadata` tramite `TextDSL`          | Cantagallo   | 1h      |
