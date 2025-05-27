package papyrus.DSL

import papyrus.logic.Papyrus
import papyrus.logic.metadata.Metadata
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.Level
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.LayerElement
import scala.collection.mutable.ArrayBufferg

object DSL:

  class PapyrusBuilder:
    var metadata: Metadata = Metadata()
    var content: Content = Content(Title("Default Title", 1))

    def build(): Papyrus = Papyrus(metadata, content)


  class MetadataBuilder:

    def build(): Metadata = Metadata()

  class ContentBuilder:
    var title: Title = ???
    val layerElements = ArrayBuffer[LayerElement] 
    
    def addLayerElement(element: LayerElement): Unit =
      layerElements += element
    
    def build(): Content = Content(Title("Default Title", 1))



  def papyrus(init: PapyrusBuilder ?=> Unit): Papyrus =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

  def metadata(init: MetadataBuilder ?=> Unit)(using PapyrusBuilder: PapyrusBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    PapyrusBuilder.metadata = builder.build()

  def content(init: ContentBuilder ?=> Unit)(using PapyrusBuilder: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    PapyrusBuilder.content = builder.build()
    
  def title(text: String, level: Level = 1)(using contentBuilder: ContentBuilder): Unit =
    contentBuilder.build().title = Title(text, level)
    
  def aCasoMetadata(a: String)(using metadataBuilder: MetadataBuilder): Unit =
    println(a)

  def aCasoContent(a: String)(using metadataBuilder: MetadataBuilder): Unit =
    println(a)

  papyrus:
    metadata:
      aCasoMetadata("aa")
    content:
      aCasoContent("aa")