package papyrus.logic

import HtmlConverter.HtmlLauncher
import papyrus.logic.Content.Content.Content
import papyrus.logic.metadata.Metadata.Metadata


object Papyrus:

  trait Papyrus:
    def metadata: Metadata
    def content: Content
    def build(): Unit

  object Papyrus:
    def apply(metadata: Metadata, content: Content): Papyrus =
      PapyrusImpl(metadata, content)

    private class PapyrusImpl(override val metadata: Metadata, override val content: Content) extends Papyrus:
      //Nota: prima lancia il css nella build
      override def build(): Unit = HtmlLauncher.launch("""<html>""" + "\n" + metadata.render + "\n" + content.render + "\n" + """</html>""", "Titolo")

        