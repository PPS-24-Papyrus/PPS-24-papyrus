package papyrus.DSL

import HtmlConverter.HtmlLauncher
import papyrus.logic.Papyrus
import papyrus.logic.metadata.Metadata
import papyrus.logic.content.Content
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.daCancellare.PapyrusElement.MetadataSyntax.title
import papyrus.logic.builders.{TextBuilder, TitleBuilder}
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}

import java.util.Optional
import scala.collection.mutable.ArrayBuffer

object DSL:

  class PapyrusBuilder:
    var metadata: Metadata = Metadata()
    var content: Content = Content(Optional.empty)

    def build(): Unit = Papyrus(metadata, content).build()

  class MetadataBuilder:
    def build(): Metadata = Metadata()

  class ContentBuilder:
    var title: Optional[Title] = Optional.empty
    val layerElements = ArrayBuffer[LayerElement]()

    def addLayerElement(element: LayerElement): Unit =
      layerElements += element

    def build(): Content = Content(title, layerElements.toSeq*)

  class LayerElementBuilder:
    def build(el: LayerElement): LayerElement = el


  def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
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


  def title(init: TitleBuilder ?=> TextDSL)(using cb: ContentBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()
    val textWrapper = init
    builder.title = textWrapper.str
    cb.title = Optional.of(builder.build())

  def text(init: TextBuilder ?=> TextDSL)(using cb: ContentBuilder): Unit =
    given builder: TextBuilder = TextBuilder()
    val textWrapper = init
    builder.value = textWrapper.str
    cb.addLayerElement(builder.build())

  class TextDSL(val str: String):
    def titleColor(c: ColorString)(using tb: TitleBuilder): TextDSL =
      tb.title = str
      tb.textColor = c
      str

    def alignment(t: Alignment)(using tb: TitleBuilder): TextDSL =
      tb.title = str
      tb.textAlign = t
      str

    def fontSize(fs: FontSize)(using tb: TitleBuilder): TextDSL =
      tb.title = str
      tb.fontSize = fs
      str

    def fontFamily(ff: FontFamily)(using tb: TitleBuilder): TextDSL =
      tb.title = str
      tb.font = ff
      str

    def color(c: ColorString)(using tb: TextBuilder): TextDSL =
      tb.value = str
      tb.color = c
      str

    def fontWeight(w: FontWeight)(using tb: TextBuilder): TextDSL =
      tb.value = str
      tb.fontWeight = w
      str

    def fontStyle(s: FontStyle)(using tb: TextBuilder): TextDSL =
      tb.value = str
      tb.fontStyle = s
      str

    def textDecoration(d: TextDecoration)(using tb: TextBuilder): TextDSL =
      tb.value = str
      tb.textDecoration = d
      str

  given Conversion[String, TextDSL] with
    def apply(str: String): TextDSL = TextDSL(str)

  @main def provaFunc(): Unit =
    papyrus:
      content:
        title:
          "titolo" titleColor "orange" fontSize 12 alignment "left"
        text:
          "Questo è un paragrafo." color "red" textDecoration "underline"







