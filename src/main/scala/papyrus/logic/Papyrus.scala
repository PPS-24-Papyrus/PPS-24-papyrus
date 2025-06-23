package papyrus.logic

import papyrus.logic.Renderer.Text.*
import papyrus.logic.content.Content
import papyrus.logic.metadata.Metadata
import papyrus.logic.utility.HtmlConverter.PapyrusPrinter

/** Represents a complete Papyrus document with metadata and content */
trait Papyrus:
  /** Document metadata (title, style, language, etc.) */
  def metadata: Metadata

  /** Document content (sections, elements, etc.) */
  def content: Content

  /** Builds and launches the document as an HTML file */
  def build(): Unit


object Papyrus:

  /** Creates a Papyrus instance from metadata and content */
  def apply(metadata: Metadata, content: Content): Papyrus =
    PapyrusImpl(metadata, content)

  private class PapyrusImpl(override val metadata: Metadata, override val content: Content) extends Papyrus:

    private val css: StyleText = ("\n" + metadata.renderStyle + "\n" + content.renderStyle).toStyleText
    private val html: MainText = (s"<!DOCTYPE html>\n  <html>" + "\n  " + metadata.render + "\n  " + content.render + "\n" + """</html>""").toMainText

    override def build(): Unit =
        PapyrusPrinter.launchFile(html, css, "Papyrus", metadata.extension, metadata.nameFile, metadata.savingPath match 
          case "" => None case path => Some(path))
