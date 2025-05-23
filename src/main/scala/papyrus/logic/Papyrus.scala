package papyrus.logic

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
      override def build(): Unit = ???
        