package HtmlConverter

import org.xhtmlrenderer.pdf.ITextRenderer
import papyrus.logic.utility.TypesInline.Extension

import java.awt.Desktop
import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path}

object HtmlLauncher:

  def launchFile(htmlContent: String, cssContent: String, title: String, extension: Extension): Unit =
    val tempDir: Path = Files.createTempDirectory(s"${title}_tmp")

    // 1. Scrive il file CSS
    val cssPath = tempDir.resolve("style.css")
    Files.write(cssPath, cssContent.getBytes())

    // 2. Scrive il file HTML con riferimento al file CSS appena scritto
    val htmlPath: Path = tempDir.resolve("index.html")
    Files.write(htmlPath, htmlContent.getBytes())

    extension match
      case "html" => this.launchHTMLWithCSS(htmlPath)
      case _ => generatePDFWithCSS(tempDir, htmlPath)


  private def launchHTMLWithCSS(htmlPath: Path): Unit =
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.browse(htmlPath.toFile.toURI)

  private def generatePDFWithCSS(tempDir: Path, htmlPath: Path): Unit =
    val pdfFile = tempDir.resolve("output.pdf").toFile
    val os = new FileOutputStream(pdfFile)

    val renderer = ITextRenderer()
    renderer.setDocument(htmlPath.toUri.toString)
    renderer.layout()
    renderer.createPDF(os)
    os.close()

    //pdfFile
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.open(pdfFile)

