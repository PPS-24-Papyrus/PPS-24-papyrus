## Get Started

Per iniziare a usare **Papyrus**, hai due opzioni:

---

### Opzione 1 – Uso diretto via `build.sbt`

Il modo più semplice per includere Papyrus è tramite questa configurazione nel tuo `build.sbt`:

```scala
lazy val root = (project in file("."))
  .settings(
    name := "test-papyrus",
    libraryDependencies += "com.github.PPS-24-Papyrus" %% "PPS-24-papyrus" % "v1.0.0" from
    "https://github.com/PPS-24-Papyrus/PPS-24-papyrus/releases/download/v1.0.0/papyrus-library-1.0.0.jar",
    libraryDependencies += "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.1.22"
)
```

> In questo modo **non serve scaricare nulla manualmente**: SBT scaricherà la libreria direttamente dal link remoto.

---

### Opzione 2 – Manuale (con JAR scaricato)

1. Scarica manualmente il JAR dalla [release ufficiale 1.0.0](https://github.com/PPS-24-Papyrus/PPS-24-papyrus/releases/tag/v1.0.0).
2. Inserisci il file nella cartella `lib/` del tuo progetto.
3. Aggiungi al `build.sbt`:

```scala
unmanagedBase := baseDirectory.value / "lib"
```

> Anche se eviti l’import con URL, SBT gestirà comunque la build del progetto Scala.

---

### Utilizzo base

Per iniziare a scrivere un documento, importa la libreria e crea un oggetto che estende `PapyrusApplication`:

```scala
import papyrus.dsl.DSL.PapyrusApplication

object MyDoc extends PapyrusApplication:

papyrus:
    ...
```

---

### Output

- Il tipo di output (`pdf` o `html`) si imposta nel blocco `metadata`, tramite il campo `extension`.

```scala
  papyrus:
    metadata:
      extension:
        "html"
```

- Se non viene specificato, il formato di default è **PDF**.
- Se non viene fornito un percorso di salvataggio, il file sarà generato in una **cartella temporanea non persistente**.
```scala
  papyrus:
    metadata:
      path:
        "C:\\Esempio\\URL\\Dove\\Salvare\\Output"
```

Per un esempio completo, visita l' [esempio d'uso](https://pps-24-papyrus.github.io/PPS-24-papyrus/report/6-example.html).
