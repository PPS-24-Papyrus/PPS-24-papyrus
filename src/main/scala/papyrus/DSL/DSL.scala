package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.builders.ImageBuilder.caption
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, MainStyleBuilder, MetadataBuilder, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TextDSL, TitleBuilder, TitleHandler}
import papyrus.DSL.builders.RowBuilder.|
import papyrus.DSL.builders.TextBuilder.{newLine, *}
import papyrus.DSL.builders.TitleBuilder.*
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
    pb.withMetadata(builder.build)

  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.withContent(builder.build)

  def title(init: TitleBuilder ?=> TitleBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder() // viene passato a init

    val configuredBuilder = init // contiene textColor, fontSize, ecc.

    val baseTitle = configuredBuilder.build.title

    val numberedTitle = ctx match
      case _: ContentBuilder => baseTitle
      case _: SectionBuilder => s"${SectionCounter.nextSection()} $baseTitle"
      case _: SubSectionBuilder => s"${SectionCounter.nextSubsection()} $baseTitle"

    val numberedBuilder = ctx match
      case _: ContentBuilder => configuredBuilder.title(numberedTitle).level(1)
      case _: SectionBuilder => configuredBuilder.title(numberedTitle).level(2)
      case _: SubSectionBuilder => configuredBuilder.title(numberedTitle).level(3)

    ctx match
      case cb: ContentBuilder => cb.setTitle(numberedBuilder.build)
      case sb: SectionBuilder => sb.setTitle(numberedBuilder.build)
      case ssb: SubSectionBuilder => ssb.setTitle(numberedBuilder.build)


  def section(init: SectionBuilder ?=> Unit)(using cb: ContentBuilder): Unit =
    given builder: SectionBuilder = SectionBuilder()
    init
    cb.addLayerElement(builder.build)

  def subsection(init: SubSectionBuilder ?=> Unit)(using cb: SectionBuilder): Unit =
    given builder: SubSectionBuilder = SubSectionBuilder()
    init
    cb.addLayerElement(builder.build)

  def listing(init: ListBuilder ?=> Unit)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: ListBuilder = ListBuilder()
    init
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build)
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build)
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build)

  def listType(init: ListBuilder ?=> ListType)(using ctx: ListBuilder): Unit =
    init
    ctx.listType(init)

  def item(init: ItemBuilder ?=> ItemBuilder)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()
    val updatedBuilder = init
    ctx.addItem(updatedBuilder.build)

  def applyTextStyle(init: TextBuilder ?=> TextBuilder, style: TextBuilder => TextBuilder)(
    using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =

    given baseBuilder: TextBuilder = TextBuilder()

    val updatedBuilder = style(init)

    ctx match
      case cb: ContentBuilder => cb.addLayerElement(updatedBuilder.build)
      case sb: SectionBuilder => sb.addLayerElement(updatedBuilder.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(updatedBuilder.build)

  def text(init: TextBuilder ?=> TextBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, identity)

  def bold(init: TextBuilder ?=> TextBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontWeight("bold"))

  def italic(init: TextBuilder ?=> TextBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontStyle("italic"))

  def underline(init: TextBuilder ?=> TextBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.textDecoration("underline"))

  def nameFile(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withNameFile(init.str)

  def extension(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withExtension(init.str.asInstanceOf[Extension])

  def path(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withSavingPath(init.str)

  def language(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withLanguage(init.str.asInstanceOf[Language])

  def metadataTitle(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withTitle(init.str)

  def author(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withAuthor(init.str)

  def charset(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    val updated: Unit = mb.withCharset(init.str.asInstanceOf[Charset])

  def styleSheet(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    mb.withStyleSheet(init.str)

  def style(init: MainStyleBuilder ?=> Unit)(using mb: MetadataBuilder): Unit =
    given builder: MainStyleBuilder = MainStyleBuilder()
    init
    mb.withStyle(builder.build)

  def font(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withFont(init.str.asInstanceOf[FontFamily])

  def fontSize(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withFontSize(init.str.asInstanceOf[FontSize])

  def lineHeight(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withLineHeight(init.str.asInstanceOf[LineHeight])

  def textColor(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withTextColor(init.str.asInstanceOf[ColorString])

  def backgroundColor(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withBackgroundColor(init.str.asInstanceOf[ColorString])

  def textAlign(init: MainStyleBuilder ?=> TextDSL)(using msb: MainStyleBuilder): Unit =
    msb.withTextAlign(init.str.asInstanceOf[Alignment])

  def margin(init: MainStyleBuilder ?=> Int)(using msb: MainStyleBuilder): Unit =
    msb.withMargin(init.asInstanceOf[Margin])

  def image(init: ImageBuilder ?=> ImageBuilder)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    val builder = init(using ImageBuilder())
    val image = builder.build

    ctx match
      case cb: ContentBuilder => cb.addLayerElement(image)
      case sb: SectionBuilder => sb.addLayerElement(image)
      case ssb: SubSectionBuilder => ssb.addLayerElement(image)


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
        cb.addLayerElement(builder.build)
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build)
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build)

  def table(init: TableBuilder ?=> Unit)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    ctx match
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build)
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build)
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build)

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
      rowBuilder.build

  given Conversion[String, TextDSL] with
    def apply(str: String): TextDSL = TextDSL(str)

  given Conversion[String, TextBuilder] with
    def apply(str: String): TextBuilder = TextBuilder(str)

  given Conversion[String, TitleBuilder] with
    def apply(str: String): TitleBuilder = TitleBuilder(str)

  given Conversion[String, ItemBuilder] with
    def apply(str: String): ItemBuilder = ItemBuilder(str)

  given Conversion[String, ImageBuilder] with
    def apply(str: String): ImageBuilder = ImageBuilder(str)

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
        style:
          margin:
            150
          font:
            "Arial"
      content:
        title:
          "End 3rd Sprint"
        bold:
          "Normale"
        section:
          title:
            "Table and listing"
          text:
            "Let's try to print a table." newLine "Ciao"
          table:
            "1" | "2" | "3"
            "4" | "5" | "6"
            "7" | "8" | "9"
            caption:
              "This is our first table:"
            alignTable:
              "center"
          subsection:
            title:
              "Listing"
            underline:
              "Prova grassetto"
            text:
              "\nWhy do we use it?\nIt is a long established"
            listing:
              listType:
                "ul"
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
