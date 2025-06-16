package papyrus.logic

import papyrus.logic.Renderer.Text.*
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata
import papyrus.logic.utility.HtmlConverter.HtmlLauncher

trait Papyrus:
  def metadata: Metadata
  def content: Content
  def build(): Unit

object Papyrus:
  def apply(metadata: Metadata, content: Content): Papyrus =
    PapyrusImpl(metadata, content)

  private class PapyrusImpl(override val metadata: Metadata, override val content: Content) extends Papyrus:

    private val css: StyleText = ("\n" + metadata.renderStyle + "\n" + content.renderStyle).toStyleText
    private val html: MainText = (s"<!DOCTYPE html>\n  <html>" + "\n  " + metadata.render + "\n  " + content.render + "\n" + """</html>""").toMainText

    override def build(): Unit =
        HtmlLauncher.launchFile(html, css, "Papyrus", metadata.extension, metadata.nameFile, metadata.savingPath match 
          case "" => None case path => Some(path))
