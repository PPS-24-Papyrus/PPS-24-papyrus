package papyrus.DSL

import HtmlConverter.HtmlLauncher
import papyrus.logic.Papyrus
import papyrus.logic.metadata.Metadata
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.{Alignment, ColorString, FontFamily, FontSize, Level}
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

  class LayerElementBuilder:
    def build(el: LayerElement): LayerElement = el


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

  class TitleBuilder:
    var title: String = "Default Title"
    var level: Level = 1
    var font: FontFamily = "Georgia"
    var fontSize: FontSize = 24
    var textColor: ColorString = "red"
    var textAlign: Alignment = "left"

    def build(): Title = Title(title, level)(font=font, fontSize=fontSize, textColor=textColor, textAlign=textAlign)

  def title(init: TitleBuilder ?=> Unit)(using cb: ContentBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()
    init
    cb.title = builder.build()

  extension (title: String)
    def font(font: FontFamily)(using tb: TitleBuilder): String =
      tb.title = title
      tb.font = font
      title

    def fontSize(size: FontSize)(using tb: TitleBuilder): String =
      tb.title = title
      tb.fontSize = size
      title

    def textColor(color: ColorString)(using tb: TitleBuilder): String =
      tb.title = title
      tb.textColor = color
      title

    def textAlign(alignment: Alignment)(using tb: TitleBuilder): String =
      tb.title = title
      tb.textAlign = alignment
      title


  infix def text(txt: String)(using cb: ContentBuilder): Unit =
    cb.addLayerElement(Text(txt)())


  @main def provaFunc(): Unit =
    val pap: Papyrus =
      papyrus:
        content:
          title:
            "Titolo carino" font "Arial" fontSize 20 textColor "red"
          text("Ciao a tutti")
    
    pap.build()
