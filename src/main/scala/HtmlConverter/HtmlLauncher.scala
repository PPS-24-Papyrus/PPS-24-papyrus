package HtmlConverter

import java.awt.Desktop
import java.nio.file.{Files, Path}

object HtmlLauncher:

  /** Salva prima il CSS nella cartella, poi salva l'HTML che lo richiama, poi apre l'HTML */
  def launchHTMLWithCSS(htmlContent: String, cssContent: String, title: String): Unit =
    // Crea una cartella temporanea con nome derivato dal titolo
    val tempDir: Path = Files.createTempDirectory(s"${title}_tmp")

    // 1. Scrive il file CSS
    val cssPath = tempDir.resolve("style.css")
    Files.write(cssPath, cssContent.getBytes())

    // 2. Scrive il file HTML con riferimento al file CSS appena scritto
    val htmlPath = tempDir.resolve("index.html")
    Files.write(htmlPath, htmlContent.getBytes())

    // 3. Apre il file HTML nel browser
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.browse(htmlPath.toFile.toURI)
