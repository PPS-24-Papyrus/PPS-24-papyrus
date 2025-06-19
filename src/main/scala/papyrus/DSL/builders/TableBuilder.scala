package papyrus.DSL.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}

import scala.annotation.targetName
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import papyrus.logic.Renderer.Text.*


class TableBuilder[T] extends LayerElementBuilder:
  private var caption: Option[String] = None
  private val rows: ListBuffer[RowBuilder[T]] = ListBuffer.empty
  var backgroundColor: ColorString = DefaultValues.backgroundColorTable
  var margin: Margin = DefaultValues.marginTable
  var textAlign: Alignment = DefaultValues.textAlignTable
  var width: Width = DefaultValues.widthTable
  var alignment: Align = DefaultValues.alignTable
  private var functionRender: T => String = t => t.toString


  def withFunctionRender(f: T => String): TableBuilder[T] =
    this.functionRender = f
    this
  
  def withCaption(caption: String): TableBuilder[T] =
    this.caption = Some(caption)
    this

  def addRow(row: RowBuilder[T]): TableBuilder[T] =
    rows += row
    this

  override def build: Table[T] = Table(caption, rows.map(_.build).toList, TableStyle(backgroundColor, margin, textAlign, width, alignment), t => MainText(functionRender(t)))


case class RowBuilder[T](private val cells: ArrayBuffer[CellBuilder[T]]) extends Builder[Row[T]]:
  def addCell(cell: CellBuilder[T]): RowBuilder[T] =
    cells += cell
    this

  @targetName("addStringCell")
  def |(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell))

  @targetName("addHeaderCell")
  def hsh(cell: T): RowBuilder[T] = addCell(CellBuilder().withContent(cell).asHeader())

  @targetName("addSimpleCell")
  def s(cell: T): RowBuilder[T] = |(cell)

  @targetName("addColspanCell")
  def |-(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell) withColspan(2))

  @targetName("addRowspanCell")
  def |^(cell: T): RowBuilder[T] = addCell(CellBuilder() withContent(cell) withRowspan(2))

  def build: Row[T] = Row(cells.map(_.build).toList)

object RowBuilder:
  def apply[T](): RowBuilder[T] = new RowBuilder(ArrayBuffer.empty[CellBuilder[T]])

  extension [T](str: T)
    def |(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c))
    def hsh(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.asHeader(), _.withContent(c).asHeader())
    def |-(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c).withColspan(2))
    def |^(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, identity, _.withContent(c).withRowspan(2))
    def ^|(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withRowspan(2), _.withContent(c))
    def ^|^(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withRowspan(2), _.withContent(c).withRowspan(2))
    def -|(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withColspan(2), _.withContent(c))
    def -|-(c: T)(using tb: TableBuilder[T]): RowBuilder[T] = makeRow(c, _.withColspan(2), _.withContent(c).withColspan(2))
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
