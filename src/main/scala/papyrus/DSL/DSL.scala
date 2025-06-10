package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.builders.{ContentBuilder, ItemBuilder, ListBuilder, MainStyleBuilder, MetadataBuilder, PapyrusBuilder, SectionBuilder, SubSectionBuilder, TextBuilder, TextDSL, TitleBuilder, TitleHandler}
import papyrus.logic.builders.{CellBuilder, ContentBuilder, ImageBuilder, MainStyleBuilder, MetadataBuilder, PapyrusBuilder, RowBuilder, TableBuilder, TextBuilder, TextDSL, TitleBuilder}
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}
import papyrus.logic.utility.SectionCounter

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

  def title(init: TitleBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()

    val baseTitle = init.str
    val numberedTitle = ctx match
      case _: ContentBuilder => baseTitle
      case _: SectionBuilder => SectionCounter.nextSection() + " " + baseTitle
      case _: SubSectionBuilder => SectionCounter.nextSubsection() + " " + baseTitle

    builder.title = numberedTitle

    ctx match
      case cb: ContentBuilder =>
        builder.level = 1
        cb.setTitle(builder.build())
      case sb: SectionBuilder =>
        builder.level = 2
        sb.setTitle(builder.build())
      case ssb: SubSectionBuilder =>
        builder.level = 3
        ssb.setTitle(builder.build())


  def section(init: SectionBuilder ?=> Unit)(using cb: ContentBuilder): Unit =
    given builder: SectionBuilder = SectionBuilder()
    init
    cb.addLayerElement(builder.build())

  def subsection(init: SubSectionBuilder ?=> Unit)(using cb: SectionBuilder): Unit =
    given builder: SubSectionBuilder = SubSectionBuilder()
    init
    cb.addLayerElement(builder.build())

  def listing(init: ListBuilder ?=> Unit)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: ListBuilder = ListBuilder()
    init
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build())
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build())
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build())

  def item(init: ItemBuilder ?=> TextDSL)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()
    builder.value = init.str
    ctx.addItem(builder.build())



  def text(init: TextBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TextBuilder = TextBuilder()
    val textWrapper = init
    builder.value = textWrapper.str
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build())
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build())
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build())

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

  def style(init: MainStyleBuilder ?=> Unit)(using mb: MetadataBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    mb.withStyle(builder.build())

  def font(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.font = init.str.asInstanceOf[FontFamily]

  def fontSize(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.fontSize = init.str.asInstanceOf[FontSize]

  def lineHeight(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.lineHeight = init.str.asInstanceOf[LineHeight]

  def textColor(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.textColor = init.str.asInstanceOf[ColorString]

  def backgroundColor(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.backgroundColor = init.str.asInstanceOf[ColorString]

  def textAlign(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.textAlign = init.str.asInstanceOf[Alignment]

  def margin(init: MainStyleBuilder ?=> Int)(using msb: MainStyleBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    msb.margin = init.asInstanceOf[Margin]

  def image(init: ImageBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: ImageBuilder = ImageBuilder()
    val textWrapper = init
    builder.src = textWrapper.str
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build())
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build())
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build())

  def tableWithList(init: TableBuilder ?=> List[Row[String]])(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    val rowsWrapper = init
    for row <- rowsWrapper yield
      val rowBuilder = RowBuilder()
      for cell <- row.cells do
        rowBuilder.addCell(CellBuilder().withContent(cell.content))
      builder.addRow(rowBuilder)
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build())
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build())
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build())

  def table(init: TableBuilder ?=> Unit)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build())
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build())
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build())

  def caption(init: TableBuilder ?=> TextDSL)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.withCaption(init.str)

  def backgroundColorTable(init: TableBuilder ?=> TextDSL)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.backgroundColor = init.str.asInstanceOf[ColorString]

  def marginTable(init: TableBuilder ?=> Int)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.margin = init.asInstanceOf[Margin]

  def textAlignTable(init: TableBuilder ?=> TextDSL)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.textAlign = init.str.asInstanceOf[Alignment]

  def widthTable(init: TableBuilder ?=> TextDSL)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.width = init.str.asInstanceOf[Width]

  def alignTable(init: TableBuilder ?=> TextDSL)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.alignment = init.str.asInstanceOf[Align]

  given Conversion[List[String], Row[String]] with
    def apply(list: List[String]): Row[String] =
      val rowBuilder = RowBuilder()
      for str <- list do
        rowBuilder.addCell(CellBuilder().withContent(str))
      rowBuilder.build()

  given Conversion[String, TextDSL] with
    def apply(str: String): TextDSL = TextDSL(str)



  @main def provaFunc(): Unit =
    papyrus:
      metadata:
        nameFile:
          "Third Sprint"
        language:
          "en"
        author:
          "LucaDani"
        extension:
          "html"
      content:
        title:
          "End 3rd Sprint"
        section:
          title:
            "Table and listing"
          text:
            "Let's try to print a table."
          table:
            "1" | "2" | "3"
            "4" | "5" | "6"
            "7" | "8" | "9"
            caption:
              "This is our first table:"
            alignTable:
              "right"
          subsection:
            title:
              "Listing"
            text:
              "This is our first list:"
            listing:
              item:
                "First element"
              item:
                "Second element"
              item:
                "Third element"
        section:
          title:
            "Image"
          text:
            "This is our first image:"
          image:
            "src/main/resources/PapyrusLogo.png" caption "This is papyrus logo" alternative "No image found" width 200


