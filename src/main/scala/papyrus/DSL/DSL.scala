package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.builders.{ContentBuilder, MetadataBuilder, PapyrusBuilder, TextBuilder, TitleBuilder, TextDSL}
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}

import java.util.Optional

object DSL:

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
    cb.setTitle(builder.build())

  def text(init: TextBuilder ?=> TextDSL)(using cb: ContentBuilder): Unit =
    given builder: TextBuilder = TextBuilder()
    val textWrapper = init
    builder.value = textWrapper.str
    cb.addLayerElement(builder.build())

  def nameFile(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withNameFile(init.str)

  def extension(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withExtension(init.str.asInstanceOf[Extension])

  def language(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withLanguage(init.str.asInstanceOf[Language])

  def metadataTitle(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withTitle(init.str)

  def author(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withAuthor(init.str)

  def charset(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    val updated: Unit = mb.withCharset(init.str.asInstanceOf[Charset])

  def styleSheet(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withStyleSheet(init.str)


  given Conversion[String, TextDSL] with
    def apply(str: String): TextDSL = TextDSL(str)

  @main def provaFunc(): Unit =
    papyrus:
      metadata:
        nameFile:
          "ciao"
        language:
          "it"
        author:
          "Luca"
      content:
        title:
          "titolo" titleColor "orange" fontSize 37 alignment "left"
        text:
          "Questo è un paragrafo." color "red" textDecoration "underline"
        text:
          "Tentativo"
        text:
          "Un altro paragrafo." color "blue" fontWeight "bold" fontStyle "italic"








