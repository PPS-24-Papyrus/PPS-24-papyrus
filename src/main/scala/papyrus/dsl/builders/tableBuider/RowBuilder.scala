package papyrus.dsl.builders.tableBuider

import papyrus.logic.layerElement.captionElement.{Cell, Row}

import scala.collection.mutable.ArrayBuffer
import papyrus.dsl.builders.Builder


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
      builder addCell left(CellBuilder[T]() withContent str)
      builder addCell right(CellBuilder[T]() withContent c)
      tb addRow builder
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
