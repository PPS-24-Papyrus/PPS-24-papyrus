package HtmlConverter

import org.xhtmlrenderer.pdf.ITextRenderer
import papyrus.DSL.DefaultValues
import papyrus.logic.utility.TypesInline.Extension

import java.awt.Desktop
import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path}

object HtmlLauncher:

  private val cssContentPdf =
    """
      |@page {
      |  margin-top: 4cm;
      |  margin-bottom: 4cm;
      |  @bottom-center {
      |    content: counter(page);
      |    font-size: 10pt;
      |  }
      |}
      |@page :first {
      |  margin-top: 1cm; /* Margine diverso per la prima pagina */
      |}
    """.stripMargin


  def launchFile(htmlContent: String, cssContent: String, title: String, extension: Extension, nameFile: String): Unit =
    val tempDir: Path = Files.createTempDirectory(s"${title}_tmp")

    val cssPath = tempDir.resolve(DefaultValues.styleSheet)
    Files.write(cssPath, if extension == "pdf" then (cssContent + "\n" +  cssContentPdf).getBytes() else cssContent.getBytes())

    val htmlPath: Path = tempDir.resolve(s"$nameFile.html")
    Files.write(htmlPath, htmlContent.getBytes())

    extension match
      case "html" => this.launchHTMLWithCSS(htmlPath)
      case _ => generatePDFWithCSS(tempDir, htmlPath, nameFile)


  private def launchHTMLWithCSS(htmlPath: Path): Unit =
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.browse(htmlPath.toFile.toURI)

  private def generatePDFWithCSS(tempDir: Path, htmlPath: Path, nameFile: String): Unit =
    val pdfFile = tempDir.resolve(s"$nameFile.pdf").toFile
    val os = new FileOutputStream(pdfFile)

    val renderer = ITextRenderer()
    renderer.setDocument(htmlPath.toUri.toString)
    renderer.layout()
    renderer.createPDF(os)
    os.close()

    if Desktop.isDesktopSupported then
      Desktop.getDesktop.open(pdfFile)