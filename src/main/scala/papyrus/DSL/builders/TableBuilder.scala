package papyrus.DSL.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}

import scala.annotation.targetName
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import papyrus.logic.Renderer.Text.*

private enum FieldTable:
  case Caption, BackgroundColor, Margin, TextAlign, Width, Align, FunctionRender

/** Builder for constructing a styled table with rows and custom rendering */
class TableBuilder[T] extends LayerElementBuilder:
  private var caption: Option[String] = None
  private val rows: ListBuffer[RowBuilder[T]] = ListBuffer.empty
  private var backgroundColor: ColorString = DefaultValues.backgroundColorTable
  private var margin: Margin = DefaultValues.marginTable
  private var textAlign: Alignment = DefaultValues.textAlignTable
  private var width: Width = DefaultValues.widthTable
  private var alignment: Align = DefaultValues.alignTable
  private var functionRender: T => String = t => t.toString

  private val modifiedFields = scala.collection.mutable.Set.empty[FieldTable]

  private def setOnce[R](field: FieldTable, setter: R => Unit)(value: R): TableBuilder[T] =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    setter(value)
    modifiedFields += field
    this

  /** Sets the function used to render cell content as string (only once) */
  def withFunctionRender(function: T => String): TableBuilder[T] =
    setOnce(FieldTable.FunctionRender, (v: T => String) => functionRender = v)(function)

  /** Sets the table caption (only once) */
  def withCaption(caption: String): TableBuilder[T] =
    setOnce(FieldTable.Caption, (v: String) => this.caption = Some(v))(caption)

  /** Sets the margin of the table (only once) */
  def margin(margin: Margin): TableBuilder[T] =
    setOnce(FieldTable.Margin, (v: Margin) => this.margin = v)(margin)

  /** Sets the background color of the table (only once) */
  def backgroundColor(color: ColorString): TableBuilder[T] =
    setOnce(FieldTable.BackgroundColor, (v: ColorString) => backgroundColor = v)(color)

  /** Sets the text alignment inside the table (only once) */
  def textAlign(align: Alignment): TableBuilder[T] =
    setOnce(FieldTable.TextAlign, (v: Alignment) => textAlign = v)(align)

  /** Sets the width of the table (only once) */
  def width(width: Width): TableBuilder[T] =
    setOnce(FieldTable.Width, (v: Width) => this.width = v)(width)

  /** Sets the horizontal alignment of the table on the page (only once) */
  def alignment(align: Align): TableBuilder[T] =
    setOnce(FieldTable.Align, (v: Align) => alignment = v)(align)

  /** Adds a row to the table */
  def addRow(row: RowBuilder[T]): TableBuilder[T] =
    rows += row
    this

  override def build: Table[T] = Table(caption, rows.map(_.build).toList, TableStyle(backgroundColor, margin, textAlign, width, alignment), t => MainText(functionRender(t)))


/** Builder for creating a row of cells in a table */
case class RowBuilder[T](private val cells: ArrayBuffer[CellBuilder[T]]) extends Builder[Row[T]]:

  /** Adds a cell to the row */
  def addCell(cell: CellBuilder[T]): RowBuilder[T] =
    cells += cell
    this

  /** Adds a standard data cell with content */
  def |(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell))

  /** Adds a header cell */
  def hsh(cell: T): RowBuilder[T] = addCell(CellBuilder().withContent(cell).asHeader())

  /** Adds a standard data cell after header cell */
  def s(cell: T): RowBuilder[T] = |(cell)

  /** Adds a cell with colspan = 2 */
  def |-(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell) withColspan(2))

  /** Adds a cell with rowspan = 2 */
  def |^(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell) withRowspan(2))

  /** Builds the final Row[T] object */
  def build: Row[T] = Row(cells.map(_.build).toList)

object RowBuilder:
  /** Creates an empty RowBuilder */
  def apply[T](): RowBuilder[T] = new RowBuilder(ArrayBuffer.empty[CellBuilder[T]])

  extension [T](str: T)
    /** Creates a row with two standard cells */
    def |(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c))

    /** Creates a row with two header cells */
    def hsh(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.asHeader(), _.withContent(c).asHeader())

    /** Creates a row with second cell having colspan = 2 */
    def |-(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c).withColspan(2))

    /** Creates a row with second cell having rowspan = 2 */
    def |^(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c).withRowspan(2))

    /** Creates a row with first cell rowspan = 2 */
    def ^|(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withRowspan(2), _.withContent(c))

    /** Creates a row where both cells have rowspan = 2 */
    def ^|^(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withRowspan(2), _.withContent(c).withRowspan(2))

    /** Creates a row with first cell colspan = 2 */
    def -|(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withColspan(2), _.withContent(c))

    /** Creates a row where both cells have colspan = 2 */
    def -|-(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withColspan(2), _.withContent(c).withColspan(2))

    /** Creates a row with two header cells */
    def hs(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.asHeader(), _.withContent(c))

    private def makeRow(
               c: T,
               left: CellBuilder[T] => CellBuilder[T],
               right: CellBuilder[T] => CellBuilder[T]
             )(using tb: TableBuilder[T]): RowBuilder[T] =
      val builder = RowBuilder[T]()
      builder addCell(left(CellBuilder[T]() withContent(str)))
      builder addCell(right(CellBuilder[T]() withContent(c)))
      tb addRow(builder)
      builder

case class CellBuilder[T](
                        private val content: Option[T] = None,
                        private val head: Boolean = false,
                        private val colspan: Int = 1,
                        private val rowspan: Int = 1
                      ) extends Builder[Cell[T]]:
  def withContent(content: T): CellBuilder[T] = this.copy(content = Some(content))
  def asHeader(): CellBuilder[T] = this.copy(head = true)
  def withColspan(colspan: Int): CellBuilder[T] = this.copy(colspan = colspan)
  def withRowspan(rowspan: Int): CellBuilder[T] = this.copy(rowspan = rowspan)

  def build: Cell[T] = Cell(content.get, head, colspan, rowspan)
