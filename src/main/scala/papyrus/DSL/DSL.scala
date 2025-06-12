package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.builders.ImageBuilder.caption
import papyrus.DSL.builders.{CellBuilder, ContentBuilder, ImageBuilder, ItemBuilder, ListBuilder, MainStyleBuilder, MetadataBuilder, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder, TextBuilder, TextDSL, TitleBuilder, TitleHandler}
import papyrus.DSL.builders.RowBuilder.|
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}
import papyrus.logic.utility.SectionCounter

import java.util.Optional

object DSL:

  def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build

  def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =

    given builder: MetadataBuilder = MetadataBuilder()
    init
    pb.metadata = builder.build

  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.content = builder.build

  def title(init: TitleBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given baseBuilder: TitleBuilder = TitleBuilder()

    val baseTitle = init.str
    val numberedTitle = ctx match
      case _: ContentBuilder => baseTitle
      case _: SectionBuilder => SectionCounter.nextSection() + " " + baseTitle
      case _: SubSectionBuilder => SectionCounter.nextSubsection() + " " + baseTitle

    var updatedBuilder = baseBuilder.title(numberedTitle)

    ctx match
      case cb: ContentBuilder =>
        updatedBuilder = updatedBuilder.level(1)
        cb.setTitle(updatedBuilder.build)
      case sb: SectionBuilder =>
        updatedBuilder = updatedBuilder.level(2)
        sb.setTitle(updatedBuilder.build)
      case ssb: SubSectionBuilder =>
        updatedBuilder = updatedBuilder.level(3)
        ssb.setTitle(updatedBuilder.build)



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

  def item(init: ItemBuilder ?=> TextDSL)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()
    val updatedBuilder = builder.value(init.str)
    ctx.addItem(updatedBuilder.build)

  def applyTextStyle(init: TextBuilder ?=> TextDSL, style: TextBuilder => TextBuilder)(
    using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =

    given baseBuilder: TextBuilder = TextBuilder()

    val textWrapper = init
    val updatedBuilder = style(baseBuilder.value(textWrapper.str))

    ctx match
      case cb: ContentBuilder => cb.addLayerElement(updatedBuilder.build)
      case sb: SectionBuilder => sb.addLayerElement(updatedBuilder.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(updatedBuilder.build)

  def text(init: TextBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, identity)

  def bold(init: TextBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontWeight("bold"))

  def italic(init: TextBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontStyle("italic"))

  def underline(init: TextBuilder ?=> TextDSL)(using ctx: ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.textDecoration("underline"))


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
    mb.withStyle(builder.build)

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
          "pdf"
        style:
          margin:
            150
          font:
            "Arial"
      content:
        title:
          "End 3rd Sprint"
        text:
          "Normale"
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
            underline:
              "Prova grassetto"
            text:
              "\nWhy do we use it?\nIt is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n\n\nWhere does it come from?\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n\nThe standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n\nWhere can I get some?\nThere are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.'"
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
