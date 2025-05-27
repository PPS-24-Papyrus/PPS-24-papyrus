package papyrus.logic

import HtmlConverter.HtmlLauncher
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.metadata.Metadata
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

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

    override def build(): Unit = HtmlLauncher.launchHTMLWithCSS(html, css, "PapyrusDocument")

@main def testMinimalPapyrus(): Unit =
  // Usa metadata di default
  val meta = Metadata()

  // Contenuto testuale semplice
  val content = Content(
    Title("Ciao",1)(),
    "Questo è un semplice testo."
  )

  // Crea e costruisce il documento
  val doc = Papyrus(meta, content)
  doc.build()