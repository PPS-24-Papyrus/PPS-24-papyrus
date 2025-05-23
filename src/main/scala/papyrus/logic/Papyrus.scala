package papyrus.logic


object Papyrus:

  trait Papyrus:
    def metadata: Metadata
    def content: Content
    def build(): Unit

  object Papyrus:
    def apply() = ???


