## Processo di Sviluppo
Luca

# Metodologia

# Ruoli

# Meeting

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

# Report