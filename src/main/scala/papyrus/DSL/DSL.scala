package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, ListBuilderImpl, ListBuilderProxy, MainStyleBuilder, MetadataBuilder, MetadataBuilderProxy, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
import papyrus.DSL.builders.ImageBuilder.caption
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, MainStyleBuilder, MetadataBuilder, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
import papyrus.DSL.builders.RowBuilder.{|, |-, |^}
import papyrus.DSL.builders.TextBuilder.{newLine, *}
import papyrus.DSL.builders.TitleBuilder.*
import papyrus.logic.utility.SectionCounter


object DSL:

  /** Entry point for building a Papyrus document */
  def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

  /** Define document metadata */
  def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    var current: MetadataBuilder = MetadataBuilder()
    given MetadataBuilder = MetadataBuilderProxy(() => current, updated => current = updated)
    init
    pb.withMetadata(current)

  /** Define document content */
  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.withContent(builder)

  /** Define a title with automatic numbering depending on context */
  def title(init: TitleBuilder ?=> TitleBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()
    val configuredBuilder = init
    val baseTitle: String = configuredBuilder.build.title
    val numberedTitle: String = generateNumberedTitle(baseTitle, ctx)
    val numberedBuilder: TitleBuilder = generateLevelTitle(configuredBuilder, numberedTitle, ctx)
    ctx match
      case pb: PapyrusBuilder => pb.setTitle(numberedBuilder.build)
      case cb: ContentBuilder => cb.setTitle(numberedBuilder.build)
      case sb: SectionBuilder => sb.setTitle(numberedBuilder.build)
      case ssb: SubSectionBuilder => ssb.setTitle(numberedBuilder.build)

  private def generateNumberedTitle(baseTitle: String, ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): String =
    ctx match
      case _: SectionBuilder => s"${SectionCounter.nextSection()} $baseTitle"
      case _: SubSectionBuilder => s"${SectionCounter.nextSubsection()} $baseTitle"
      case _ => baseTitle

  private def generateLevelTitle(builder: TitleBuilder, title: String, ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): TitleBuilder =
    ctx match
      case _: SectionBuilder => builder.title(title).level(2)
      case _: SubSectionBuilder => builder.title(title).level(3)
      case _ => builder.title(title).level(1)

  /** Define a section, only valid inside Papyrus or Content */
  def section(init: SectionBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder): Unit =
    given builder: SectionBuilder = SectionBuilder()
    init
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(builder.build)
      case cb: ContentBuilder => cb.addLayerElement(builder.build)

  /** Define a subsection, only valid inside Section */
  def subsection(init: SubSectionBuilder ?=> Unit)(using cb: SectionBuilder): Unit =
    given builder: SubSectionBuilder = SubSectionBuilder()
    init
    cb.addLayerElement(builder.build)

  /** Define a list with optional nested lists and sorting */
  def listing(init: ListBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder | ListBuilder): Unit =
    var internalBuilder: ListBuilder = ctx match
      case lb: ListBuilder =>
        ListBuilderImpl(listType = lb.listType, order = lb.order, reference = lb.reference)
      case _ =>
        ListBuilderImpl()
    val proxy = ListBuilderProxy(() => internalBuilder, updated => internalBuilder = updated)
    given ListBuilder = proxy
    init
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(proxy.build)
      case cb: ContentBuilder => cb.addLayerElement(proxy.build)
      case sb: SectionBuilder => sb.addLayerElement(proxy.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(proxy.build)
      case lb: ListBuilder => lb.add(internalBuilder)

  /** Define a list item, valid only inside listing */
  def item(init: ItemBuilder ?=> ItemBuilder)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()
    ctx.add(init) // updated inside proxy

  /** Set list type ("ul" or "ol") */
  def listType(init: ListBuilder ?=> ListType)(using ctx: ListBuilder): Unit =
    ctx.withListType(init)

  /** Set list sorting ("alphabetical", "length", "reverse", "levenshtein") */
  def ordered(init: ListBuilder ?=> SortingList)(using ctx: ListBuilder): Unit =
    ctx.withSortingProperties(init)

  /** Set reference string for Levenshtein sorting */
  def reference(init: ListBuilder ?=> String)(using ctx: ListBuilder): Unit =
    ctx.withReference(init)

  private def applyTextStyle(init: TextBuilder ?=> TextBuilder, style: TextBuilder => TextBuilder)(
    using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given baseBuilder: TextBuilder = TextBuilder()
    val updatedBuilder = style(init)
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(updatedBuilder.build)
      case cb: ContentBuilder => cb.addLayerElement(updatedBuilder.build)
      case sb: SectionBuilder => sb.addLayerElement(updatedBuilder.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(updatedBuilder.build)

  /** Insert plain text */
  def text(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, identity)

  /** Insert bold text */
  def bold(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontWeight("bold"))

  /** Insert italic text */
  def italic(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontStyle("italic"))

  /** Insert underlined text */
  def underline(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.textDecoration("underline"))

  /** Set filename */
  def nameFile(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withNameFile(init)

  /** Set file extension (e.g. "pdf", "html") */
  def extension(init: MetadataBuilder ?=> Extension)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withExtension(init)

  /** Set saving path */
  def path(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withSavingPath(init)

  /** Set document language (e.g. "en", "it") */
  def language(init: MetadataBuilder ?=> Language)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withLanguage(init)

  /** Set metadata title */
  def metadataTitle(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withTitle(init)

  /** Set author */
  def author(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withAuthor(init)

  /** Set charset (e.g. "utf-8") */
  def charset(init: MetadataBuilder ?=> Charset)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withCharset(init)

  /** Set CSS style sheet file */
  def styleSheet(init: MetadataBuilder ?=> StyleSheet)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withStyleSheet(init)

  /** Set font family */
  def font(init: MetadataBuilder ?=> FontFamily)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFont(init))

  /** Set font size (pixels) */
  def fontSize(init: MetadataBuilder ?=> FontSize)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFontSize(init))

  /** Set line height (e.g. 1.5) */
  def lineHeight(init: MetadataBuilder ?=> LineHeight)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withLineHeight(init))

  /** Set text color (e.g. "#000000") */
  def textColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextColor(init))

  /** Set background color */
  def backgroundColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withBackgroundColor(init))

  /** Set text alignment ("left", "right", "center", "justify") */
  def textAlign(init: MetadataBuilder ?=> Alignment)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextAlign(init))

  /** Set margin (pixels) */
  def margin(init: MetadataBuilder ?=> Margin)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withMargin(init))

  /** Insert image with optional caption, alt, width */
  def image(init: ImageBuilder ?=> ImageBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    val builder = init(using ImageBuilder())
    val image = builder.build
    ctx match
      case pb: PapyrusBuilder     => pb.addLayerElement(image)
      case cb: ContentBuilder     => cb.addLayerElement(image)
      case sb: SectionBuilder     => sb.addLayerElement(image)
      case ssb: SubSectionBuilder => ssb.addLayerElement(image)

  /** Add list of rows to a table */
  def withList[T](init: TableBuilder[T] ?=> List[RowBuilder[T]])(using tb: TableBuilder[T]): Unit =
    val rowBuilders = init
    rowBuilders.foreach(tb.addRow)

  /** Define a table */
  def table[T](init: TableBuilder[T] ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder[T] = TableBuilder()
    init
    ctx match
      case pb: PapyrusBuilder =>
        pb.addLayerElement(builder.build)
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build)
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build)
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build)

  /** Set table caption */
  def caption[T](init: TableBuilder[T] ?=> String)(using tb: TableBuilder[T]): Unit =
    tb.withCaption(init)

  /** Set table background color */
  def backgroundColorTable[T](init: TableBuilder[T] ?=> ColorString)(using tb: TableBuilder[T]): Unit =
    tb.backgroundColor(init)

  /** Set table margin */
  def marginTable[T](init: TableBuilder[T] ?=> Margin)(using tb: TableBuilder[T]): Unit =
    tb.margin(init)

  /** Set table text alignment */
  def textAlignTable[T](init: TableBuilder[T] ?=> Alignment)(using tb: TableBuilder[T]): Unit =
    tb.textAlign(init)

  /** Set table width (pixels) */
  def widthTable[T](init: TableBuilder[T] ?=> Width)(using tb: TableBuilder[T]): Unit =
    tb.width(init)

  /** Set table horizontal alignment ("left", "right", "center") */
  def alignTable[T](init: TableBuilder[T] ?=> Align)(using tb: TableBuilder[T]): Unit =
    tb.alignment(init)

  /** Set table cell rendering function */
  def renderTable[T](init: TableBuilder[T] ?=> (T => String))(using tb: TableBuilder[T]): Unit =
    tb.withFunctionRender(init)

  /** Convert List[T] to RowBuilder[T] */
  given[T]: Conversion[List[T], RowBuilder[T]] with
    def apply(list: List[T]): RowBuilder[T] =
      val rowBuilder = RowBuilder[T]()
      for str <- list do
        rowBuilder.addCell(CellBuilder().withContent(str))
      rowBuilder

  /** Convert String to TextBuilder */
  given Conversion[String, TextBuilder] with
    def apply(str: String): TextBuilder = TextBuilder(str)

  /** Convert String to TitleBuilder */
  given Conversion[String, TitleBuilder] with
    def apply(str: String): TitleBuilder = TitleBuilder(str)

  /** Convert String to ItemBuilder */
  given Conversion[String, ItemBuilder] with
    def apply(str: String): ItemBuilder = ItemBuilder(str)

  /** Convert String to ImageBuilder */
  given Conversion[String, ImageBuilder] with
    def apply(str: String): ImageBuilder = ImageBuilder(str)


  @main def provaFunc(): Unit = {
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
        margin:
          150
      content:
        title:
          "End 3rd Sprint"
        text:
          "Normale" color "red"
        section:
          title:
            "Table and listing"
          text:
            "Let's try to print a table." newLine "Ciao" newLine "Ciao"
          table:
            withList:
              List(
                List("1", "2", "3"),
                List("4", "5", "6"),
                List("7", "8", "9")
              )
            renderTable:
              (s: String) => s
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
              "Listing" textColor "red"
            underline:
              "Prova grassetto"
            text:
              "\nWhy do we use it?\nIt is a long established" newLine "Ciao"
            listing:
              listType:
                "ol"
              item:
                "First element"
              item:
                "Second element"
        section:
          title:
            "Image"
          text:
            "This is our first image:"
          image:
            "src/main/resources/PapyrusLogo.png" caption "This is papyrus logo" alternative "No image found" width 200

  }

