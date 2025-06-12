package papyrus.logic

import HtmlConverter.HtmlLauncher
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata

trait Papyrus:
  def metadata: Metadata
  def content: Content
  def build(): Unit

object Papyrus:
  def apply(metadata: Metadata, content: Content): Papyrus =
    PapyrusImpl(metadata, content)

  private class PapyrusImpl(override val metadata: Metadata, override val content: Content) extends Papyrus:

    private val css: String = metadata.renderStyle + "\n" + content.renderStyle
    private val html: String = s"<!DOCTYPE html>\n  <html>" + "\n  " + metadata.render + "\n  " + content.render + "\n" + """</html>"""

    override def build(): Unit =
        HtmlLauncher.launchFile(html, css, "Papyrus", metadata.extension, metadata.nameFile)
