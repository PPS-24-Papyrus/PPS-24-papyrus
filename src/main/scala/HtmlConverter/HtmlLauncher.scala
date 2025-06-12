package HtmlConverter

import org.xhtmlrenderer.pdf.ITextRenderer
import papyrus.DSL.DefaultValues
import papyrus.logic.utility.TypesInline.Extension

import java.awt.Desktop
import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Path, StandardOpenOption}
import scala.util.Using

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
      |  margin-top: 1cm;
      |}
    """.stripMargin

  def launchFile(htmlContent: String, cssContent: String, title: String, extension: Extension, nameFile: String): Unit =
    val tempDir = Files.createTempDirectory(s"${title}_tmp")
    val cssFinal = if extension == "pdf" then s"$cssContent\n$cssContentPdf" else cssContent
    val htmlPath = tempDir.resolve(s"$nameFile.html")

    Files.writeString(tempDir.resolve(DefaultValues.styleSheet), cssFinal)
    Files.writeString(htmlPath, htmlContent)

    extension match
      case "html" => openInBrowser(htmlPath)
      case "pdf"  => generatePdf(htmlPath, tempDir.resolve(s"$nameFile.pdf"))
      case other  => println(s"Unsupported extension: $other")

  private def openInBrowser(path: Path): Unit =
    Option(Desktop.getDesktop).filter(_.isSupported(Desktop.Action.BROWSE)).foreach(_.browse(path.toUri))

  private def generatePdf(htmlPath: Path, pdfPath: Path): Unit =
    Using.resource(new FileOutputStream(pdfPath.toFile)): os =>
      val renderer = ITextRenderer()
      renderer.setDocument(htmlPath.toUri.toString)
      renderer.layout()
      renderer.createPDF(os)

    Option(Desktop.getDesktop).filter(_.isSupported(Desktop.Action.OPEN)).foreach(_.open(pdfPath.toFile))
