package papyrus.logic

import HtmlConverter.HtmlLauncher
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.metadata.Metadata
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.styleObjects.TitleStyle

import java.io.FileOutputStream
import java.util.Optional
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.{FileOutputStream, OutputStream}
import java.io.{File, FileOutputStream}
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder

trait Papyrus:
  def metadata: Metadata
  def content: Content
  def build(): Unit

object Papyrus:
  def apply(metadata: Metadata, content: Content): Papyrus =
    PapyrusImpl(metadata, content)

  private class PapyrusImpl(override val metadata: Metadata, override val content: Content) extends Papyrus:

    val css: String = metadata.renderStyle + "\n" + content.renderStyle
    val html: String = """<html>""" + "\n" + metadata.render + "\n" + content.render + "\n" + """</html>"""

    override def build(): Unit =
      if metadata.extension == "html" then
        HtmlLauncher.launchHTMLWithCSS(html, css, "PapyrusDocument")
      else if metadata.extension == "pdf" then
        val outputPdfPath = metadata.nameFile + ".pdf"
        convertHtmlToPdf(html, outputPdfPath)
        println(s"PDF generated at: $outputPdfPath")
      else
        throw new IllegalArgumentException(s"Unsupported file extension: ${metadata.extension}")

    def convertHtmlToPdf(htmlContent: String, outputPath: String): Unit = 
      val outputStream = new FileOutputStream(new File(outputPath))
      try
        val builder = new PdfRendererBuilder()
        builder.useFastMode()
        builder.withHtmlContent(htmlContent, null)
        builder.toStream(outputStream)
        builder.run()
      finally
        outputStream.close()