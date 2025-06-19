package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, ListBuilderProxy, MainStyleBuilder, MetadataBuilder, MetadataBuilderProxy, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TitleBuilder}
import papyrus.DSL.builders.TextBuilder.{newLine, *}
import papyrus.DSL.builders.TitleBuilder.*
import papyrus.logic.layerElement.captionElement.Row
import papyrus.logic.utility.SectionCounter


object DSL:

  def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

  def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    var current: MetadataBuilder = MetadataBuilder()
    given MetadataBuilder = MetadataBuilderProxy(() => current, updated => current = updated)
    init
    pb.withMetadata(current)

  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.withContent(builder)



  def title(init: TitleBuilder ?=> TitleBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder() // viene passato a init

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


  def section(init: SectionBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder): Unit =
    given builder: SectionBuilder = SectionBuilder()
    init
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(builder.build)
      case cb: ContentBuilder => cb.addLayerElement(builder.build)

  def subsection(init: SubSectionBuilder ?=> Unit)(using cb: SectionBuilder): Unit =
    given builder: SubSectionBuilder = SubSectionBuilder()
    init
    cb.addLayerElement(builder.build)

  def listing(init: ListBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder | ListBuilder): Unit =
    var internalBuilder = ListBuilder()
    val proxy = ListBuilderProxy(() => internalBuilder, updated => internalBuilder = updated)

    given ListBuilder = proxy

    init

    // Quando esci dal DSL, devi aggiungere la lista costruita:
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(internalBuilder.build)
      case cb: ContentBuilder => cb.addLayerElement(internalBuilder.build)
      case sb: SectionBuilder => sb.addLayerElement(internalBuilder.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(internalBuilder.build)
      case lb: ListBuilder => lb.add(internalBuilder.withListType(lb.listType).build)


  def item(init: ItemBuilder ?=> ItemBuilder)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()

    val built = init.build
    ctx.add(built) // aggiornamento avviene dentro il proxy

  def listType(init: ListBuilder ?=> ListType)(using ctx: ListBuilder): Unit =
    init
    ctx.withListType(init)

  def ordered(init: ListBuilder ?=> SortingList)(using ctx: ListBuilder): Unit =
    init
    ctx.ordered(init)

  def reverse(init: ListBuilder ?=> Unit)(using ctx: ListBuilder): Unit =
    init
    ctx.reverseListing


  def reference(init: ListBuilder ?=> String)(using ctx: ListBuilder): Unit =
    init
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

  def text(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, identity)

  def bold(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontWeight("bold"))

  def italic(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontStyle("italic"))

  def underline(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.textDecoration("underline"))

  def nameFile(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withNameFile(init)

  def extension(init: MetadataBuilder ?=> Extension)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withExtension(init)


  def path(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withSavingPath(init)

  def language(init: MetadataBuilder ?=> Language)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withLanguage(init)

  def metadataTitle(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withTitle(init)

  def author(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withAuthor(init)

  def charset(init: MetadataBuilder ?=> Charset)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withCharset(init)

  def styleSheet(init: MetadataBuilder ?=> StyleSheet)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withStyleSheet(init)

  def font(init: MetadataBuilder ?=> FontFamily)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFont(init))

  def fontSize(init: MetadataBuilder ?=> FontSize)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFontSize(init))

  def lineHeight(init: MetadataBuilder ?=> LineHeight)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withLineHeight(init))

  def textColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextColor(init))

  def backgroundColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withBackgroundColor(init))


  def textAlign(init: MetadataBuilder ?=> Alignment)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextAlign(init))

  def margin(init: MetadataBuilder ?=> Margin)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withMargin(init))

  def image(init: ImageBuilder ?=> ImageBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    val builder = init(using ImageBuilder())
    val image = builder.build

    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(image)
      case cb: ContentBuilder => cb.addLayerElement(image)
      case sb: SectionBuilder => sb.addLayerElement(image)
      case ssb: SubSectionBuilder => ssb.addLayerElement(image)


  def tableWithList(init: TableBuilder ?=> List[Row[String]])(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    val rowsWrapper = init
    for row <- rowsWrapper yield
      val rowBuilder = RowBuilder()
      for cell <- row.cells do
        rowBuilder.addCell(CellBuilder().withContent(cell.content))
      builder.addRow(rowBuilder)
    ctx match
      case pb: PapyrusBuilder =>
        pb.addLayerElement(builder.build)
      case cb: ContentBuilder =>
        cb.addLayerElement(builder.build)
      case sb: SectionBuilder =>
        sb.addLayerElement(builder.build)
      case ssb: SubSectionBuilder =>
        ssb.addLayerElement(builder.build)

  def table(init: TableBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
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

  def caption(init: TableBuilder ?=> String)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.withCaption(init)

  def backgroundColorTable(init: TableBuilder ?=> ColorString)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.backgroundColor = init

  def marginTable(init: TableBuilder ?=> Int)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.margin = init.asInstanceOf[Margin]

  def textAlignTable(init: TableBuilder ?=> Alignment)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.textAlign = init

  def widthTable(init: TableBuilder ?=> Width)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.width = init

  def alignTable(init: TableBuilder ?=> Align)(using tb: TableBuilder): Unit =
    given builder: TableBuilder = TableBuilder()
    init
    tb.alignment = init

  given Conversion[List[String], Row[String]] with
    def apply(list: List[String]): Row[String] =
      val rowBuilder = RowBuilder()
      for str <- list do
        rowBuilder.addCell(CellBuilder().withContent(str))
      rowBuilder.build

  given Conversion[String, TextBuilder] with
    def apply(str: String): TextBuilder = TextBuilder(str)

  given Conversion[String, TitleBuilder] with
    def apply(str: String): TitleBuilder = TitleBuilder(str)

  given Conversion[String, ItemBuilder] with
    def apply(str: String): ItemBuilder = ItemBuilder(str)

  given Conversion[String, ImageBuilder] with
    def apply(str: String): ImageBuilder = ImageBuilder(str)




  @main def provaFunc(): Unit = {
    papyrus:
      metadata:
        extension:
          "html"

      listing:
        item:
          "c"
        listing:
          item:
            "prima.prima"
          item:
            "aaa"
          listing:
            item:
              "prima.seconda.prima"
        item:
          "a"
        item:
          "terza"


    /*
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
  */
  }

