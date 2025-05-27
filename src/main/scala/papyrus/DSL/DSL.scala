package papyrus.DSL

import papyrus.logic.Papyrus
import papyrus.logic.metadata.Metadata
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.Level
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.daCancellare.PapyrusElement.MetadataSyntax.title
import papyrus.logic.layerElement.LayerElement

import scala.collection.mutable.ArrayBuffer

object DSL:

  class PapyrusBuilder:
    var metadata: Metadata = Metadata()
    var content: Content = Content(Title("Default Title", 1)())

    def build(): Papyrus = Papyrus(metadata, content)

  class MetadataBuilder:
    def build(): Metadata = Metadata()

  class ContentBuilder:
    var title: Title = Title("Titolo di default", 1)()
    val layerElements = ArrayBuffer[LayerElement]()

    def addLayerElement(element: LayerElement): Unit =
      layerElements += element

    def build(): Content = Content(title, layerElements.toSeq*)

  def papyrus(init: PapyrusBuilder ?=> Unit): Papyrus =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

  def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    pb.metadata = builder.build()

  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.content = builder.build()

  infix def title(text: String)(using cb: ContentBuilder): Unit =
    cb.title = Title(text, 1)()

  infix def text(txt: String)(using cb: ContentBuilder): Unit =
    cb.addLayerElement(txt)


  @main def provaFunc(): Unit =
    val pap: Papyrus =
      papyrus:
        content:
          title: "Titolo carino"
          text: "Ciao a tutti"


    pap.build()
