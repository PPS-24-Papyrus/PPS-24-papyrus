package HtmlConverter

import org.xhtmlrenderer.pdf.ITextRenderer

import java.awt.Desktop
import java.io.{File, FileOutputStream}
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

  def generatePDFWithCSS(htmlContent: String, cssContent: String, title: String): Unit =
    val tempDir: Path = Files.createTempDirectory(s"${title}_pdf_tmp")

    val cssPath = tempDir.resolve("style.css")
    Files.write(cssPath, cssContent.getBytes())

    val htmlPath = tempDir.resolve("document.html")
    Files.write(htmlPath, htmlContent.getBytes())

    val pdfFile = tempDir.resolve("output.pdf").toFile
    val os = new FileOutputStream(pdfFile)

    val renderer = new ITextRenderer()
    renderer.setDocument(htmlPath.toUri.toString) // riferimenti locali funzionano
    renderer.layout()
    renderer.createPDF(os)
    os.close()

    //pdfFile
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.open(pdfFile)

