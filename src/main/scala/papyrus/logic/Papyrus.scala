package papyrus.logic

import papyrus.logic.Content.Content.Content
import papyrus.logic.metadata.Metadata.Metadata


object Papyrus:

  trait Papyrus:
    def metadata: Metadata
    def content: Content
    def build(): Unit

  object Papyrus:
    def apply() = ???


