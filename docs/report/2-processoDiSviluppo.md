## Processo di Sviluppo

### Metodologia

Il progetto è stato sviluppato seguendo una metodologia **Scrum**. Sono stati pianificati circa sei sprint, ciascuno della durata di una settimana, con cinque giorni effettivi di lavoro e due giorni di margine per eventuali ritardi.

I task settimanali venivano gestiti tramite **Microsoft Planner**, integrato in Teams. Ogni lunedì mattina il team si riuniva per definire i compiti della settimana, la loro priorità, la persona assegnata e, quando necessario, la data prevista di svolgimento.

Durante la fase iniziale, è stata elaborata una bozza di possibili funzionalità e miglioramenti, che ha guidato la composizione degli sprint futuri. Sebbene i task venissero ridefiniti settimanalmente, il progetto si è mosso con una visione d’insieme coerente.

Il team ha cercato di seguire i principi del **Test-Driven Development (TDD)**. Tuttavia, in alcuni casi, per favorire la velocità, il comportamento del DSL veniva validato direttamente tramite `main`. A fine sprint si dedicava talvolta una fase al **refactoring**, per migliorare la leggibilità e struttura del codice mantenendone la funzionalità.

---

### Ruoli

Il team era composto da **due sviluppatori**:

- **Luca Cantagallo** (Scrum Master): si occupava della progettazione architetturale, della definizione dei task, della pianificazione settimanale e dello sviluppo della struttura interna di Papyrus.
- **Daniel Capannini**: contribuiva allo sviluppo e alla discussione delle funzionalità, proponendo estensioni e verificandone la coerenza con gli obiettivi del progetto. Ha seguito con particolare attenzione la **parte di testing**, sia con **ScalaTest** che con strumenti BDD come **Cucumber** e **Gherkin**.

Durante i primi sprint sono state discusse e definite le scelte architetturali fondamentali. Da quel momento la struttura è rimasta stabile. Ogni settimana, i task venivano organizzati e assegnati dal Scrum Master, pur lasciando spazio a osservazioni e proposte.

---

### Meeting

L’unico meeting ricorrente fisso era quello del **lunedì mattina**, dedicato alla pianificazione dello sprint.

Durante ogni sprint il team si incontrava **almeno due volte in sede** presso l’università per condividere i progressi, confrontarsi o semplicemente lavorare in parallelo su task individuali. Nei primi sprint gli incontri erano più frequenti, per definire l’architettura e impostare le funzioni principali.

Sono state effettuate **tre release principali** su `main`, sempre in corrispondenza del raggiungimento di una versione stabile. In queste occasioni, si organizzavano sessioni di merge e approvazione delle pull request in presenza.

Non è stato utilizzato un calendario condiviso né sono stati fissati altri meeting ricorrenti. Alcune decisioni venivano prese direttamente nei momenti di lavoro condiviso.

# DVCS

Nel progetto è stato utilizzato Git in combinazione con GitHub, seguendo una struttura ispirata al modello Gitflow. Sono stati mantenuti due branch principali:

- main, destinato alle release stabili,

- dev, utilizzato per lo sviluppo continuo e sufficientemente testato.

Ogni volta che si rendeva necessario sviluppare una nuova funzionalità, si partiva da dev creando un nuovo branch dedicato. Una volta completato lo sviluppo e i relativi test, si procedeva con una Pull Request per integrare le modifiche nel branch dev. Questo processo ha favorito una gestione ordinata delle modifiche e ha permesso di richiedere revisioni da parte degli altri membri del team.

# Testing

È stato seguito un approccio di sviluppo basato sul Test Driven Development (TDD) per garantire una maggiore solidità del codice. Questa metodologia prevede la definizione e l’implementazione di un test prima dello sviluppo della funzionalità vera e propria, portando così alla creazione di test completi per una parte significativa del codice.

Ad eccezione delle funzionalità più banali, il processo si è articolato in tre fasi: scrittura del test, sviluppo della funzionalità fino al superamento del test, e successiva fase di refactoring per migliorare la qualità del codice.

# CI/CD

Le GitHub Actions sono state utilizzate per automatizzare la distribuzione e la verifica del codice. Queste azioni includevano:

Distribuzione automatica della documentazione su GitHub Pages.

Test automatico del codice su push e pull request.

Generazione automatica della documentazione API tramite Scaladoc.

---

### Report

Durante lo sviluppo, il team ha annotato bozze e materiale utile per il report, tra cui l’EBNF, i diagrammi UML e le keyword implementate.

La stesura del report è avvenuta **nello sprint finale**, con la realizzazione di una **GitHub Pages**. Il report è scritto interamente in **Markdown** e aggiornato in tempo reale sul repository, con **revisioni incrociate** per garantire chiarezza e coerenza.

Ciascun membro ha scritto la propria parte sulla base delle competenze sviluppate, mantenendo uno stile condiviso e una documentazione tecnica chiara.

