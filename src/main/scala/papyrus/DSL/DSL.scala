package papyrus.DSL

import papyrus.logic.Papyrus
import papyrus.logic.metadata.Metadata
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.Level

object DSL:

  class PapyrusBuilder:
    private var metadata: Metadata = Metadata()
    private var content: Content = Content()

    def metadata(init: MetadataBuilder ?=> Unit): Unit =
      given builder: MetadataBuilder = MetadataBuilder()
      init
      metadata = builder.build()

    def content(init: ContentBuilder ?=> Unit): Unit =
      given builder: ContentBuilder = ContentBuilder()
      init
      content = builder.build()

    def build(): Papyrus = Papyrus(metadata, content)

  class MetadataBuilder:
    private var name: String = "untitled"
    private var title: String = "New Papyrus"
    private var author: String = "Unknown"

    def nameFile(value: String): Unit = name = value
    def title(value: String): Unit = title = value
    def author(value: String): Unit = author = value

    def build(): Metadata = Metadata(name, title, author)

  class ContentBuilder:
    private val elements = scala.collection.mutable.ListBuffer.empty[Any]

    def title(text: String, level: Level): Unit =
      elements += Title(text, level)

    def text(value: String): Unit =
      elements += value

    def build(): Content = Content(elements: _*)

  def papyrus(init: PapyrusBuilder ?=> Unit): Papyrus =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

    papyrus:
      metadat:
      content: