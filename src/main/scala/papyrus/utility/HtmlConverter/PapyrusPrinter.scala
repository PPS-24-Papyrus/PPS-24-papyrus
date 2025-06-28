package papyrus.utility.HtmlConverter

import org.xhtmlrenderer.pdf.ITextRenderer
import papyrus.utility.DefaultValues
import papyrus.logic.Renderer.Text.*
import papyrus.utility.TypesInline.Extension

import java.awt.Desktop
import java.io.FileOutputStream
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

object PapyrusPrinter:

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
      |
      |table {
      |  margin-left: auto;
      |  margin-right: auto;
      |}
    """.stripMargin

  /** Launches an HTML or PDF file with the given content, styles, and metadata.*/
  def launchFile(
                  htmlContent: MainText,
                  cssContent: StyleText,
                  title: String,
                  extension: Extension,
                  nameFile: String,
                  outputDirOpt: Option[String]
                ): Unit =
    val targetDir = outputDirOpt
      .map(Paths.get(_).resolve(nameFile))
      .map(path => if Files.exists(path) then path else Files.createDirectories(path))
      .getOrElse(Files.createTempDirectory(s"${title}_tmp"))

    val cssFinal = if extension == "pdf" then s"$cssContent\n$cssContentPdf" else cssContent.string
    val htmlPath = targetDir.resolve(s"$nameFile.html")
    val cssPath = targetDir.resolve(DefaultValues.styleSheet)

    Files.writeString(cssPath, cssFinal)
    Files.writeString(htmlPath, htmlContent.string)

    extension match
      case "html" => openInBrowser(htmlPath)
      case "pdf"  => generatePdf(htmlPath, targetDir.resolve(s"$nameFile.pdf"))
      case other  => println(s"Unsupported extension: $other")

  private def openInBrowser(path: Path): Unit =
    Option(Desktop.getDesktop)
      .filter(_.isSupported(Desktop.Action.BROWSE))
      .foreach(_.browse(path.toUri))

  private def generatePdf(htmlPath: Path, pdfPath: Path): Unit =
    Using.resource(new FileOutputStream(pdfPath.toFile)): os =>
      val renderer = ITextRenderer()
      renderer.setDocument(htmlPath.toUri.toString)
      renderer.layout()
      renderer.createPDF(os)

    Option(Desktop.getDesktop)
      .filter(_.isSupported(Desktop.Action.OPEN))
      .foreach(_.open(pdfPath.toFile))
