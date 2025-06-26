package papyrus.dsl

import papyrus.dsl.builders.{ContentBuilder, ImageBuilder, PapyrusBuilder, RowBuilder, SectionBuilder, SubSectionBuilder, TableBuilder}
import papyrus.logic.utility.TypesInline.*

object ImageDSL:
  /** Insert image with optional caption, alt, width */
  def image(init: ImageBuilder ?=> ImageBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    val builder = init(using ImageBuilder())
    val image = builder.build
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(image)
      case cb: ContentBuilder => cb.addLayerElement(image)
      case sb: SectionBuilder => sb.addLayerElement(image)
      case ssb: SubSectionBuilder => ssb.addLayerElement(image)


object TableDSL:
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

