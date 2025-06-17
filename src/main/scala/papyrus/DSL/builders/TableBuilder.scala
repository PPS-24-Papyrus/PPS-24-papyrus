package papyrus.DSL.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}

import scala.annotation.targetName
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import papyrus.logic.Renderer.Text.*


class TableBuilder extends Builder[Table[String]]:
  private var caption: Option[String] = None
  private val rows: ListBuffer[RowBuilder] = ListBuffer.empty
  var backgroundColor: ColorString = DefaultValues.backgroundColorTable
  var margin: Margin = DefaultValues.marginTable
  var textAlign: Alignment = DefaultValues.textAlignTable
  var width: Width = DefaultValues.widthTable
  var alignment: Align = DefaultValues.alignTable
  

  def withCaption(caption: String): TableBuilder =
    this.caption = Some(caption)
    this

  def addRow(row: RowBuilder): TableBuilder =
    rows += row
    this

  override def build: Table[String] = Table(caption, rows.map(_.build).toList, TableStyle(backgroundColor, margin, textAlign, width, alignment), MainText(_))


case class RowBuilder(private val cells: ArrayBuffer[CellBuilder]) extends Builder[Row[String]]:
  def addCell(cell: CellBuilder): RowBuilder =
    cells += cell
    this

  @targetName("addStringCell")
  def |(cell: String): RowBuilder = addCell(CellBuilder().withContent(cell))

  @targetName("addHeaderCell")
  def hsh(cell: String): RowBuilder = addCell(CellBuilder().withContent(cell).asHeader())

  @targetName("addSimpleCell")
  def s(cell: String): RowBuilder = |(cell)

  @targetName("addColspanCell")
  def |-(cell: String): RowBuilder = addCell(CellBuilder().withContent(cell).withColspan(2))

  @targetName("addRowspanCell")
  def |^(cell: String): RowBuilder = addCell(CellBuilder().withContent(cell).withRowspan(2))

  def build: Row[String] = Row(cells.map(_.build).toList)

object RowBuilder:
  def apply(): RowBuilder = new RowBuilder(ArrayBuffer.empty[CellBuilder])

  extension (str: String)
    def |(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, identity, _.withContent(c))
    def hsh(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.asHeader(), _.withContent(c).asHeader())
    def |-(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, identity, _.withContent(c).withColspan(2))
    def |^(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, identity, _.withContent(c).withRowspan(2))
    def ^|(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.withRowspan(2), _.withContent(c))
    def ^|^(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.withRowspan(2), _.withContent(c).withRowspan(2))
    def -|(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.withColspan(2), _.withContent(c))
    def -|-(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.withColspan(2), _.withContent(c).withColspan(2))
    def hs(c: String)(using tb: TableBuilder): RowBuilder = makeRow(c, _.asHeader(), _.withContent(c))

    private def makeRow(
               c: String,
               left: CellBuilder => CellBuilder,
               right: CellBuilder => CellBuilder
             )(using tb: TableBuilder): RowBuilder =
      val builder = RowBuilder()
      builder.addCell(left(CellBuilder().withContent(str)))
      builder.addCell(right(CellBuilder().withContent(c)))
      tb.addRow(builder)
      builder

case class CellBuilder(
                        private val content: String = "",
                        private val head: Boolean = false,
                        private val colspan: Int = 1,
                        private val rowspan: Int = 1
                      ) extends Builder[Cell[String]]:
  def withContent(content: String): CellBuilder = this.copy(content = content)
  def asHeader(): CellBuilder = this.copy(head = true)
  def withColspan(colspan: Int): CellBuilder = this.copy(colspan = colspan)
  def withRowspan(rowspan: Int): CellBuilder = this.copy(rowspan = rowspan)

  def build: Cell[String] = Cell(content, head, colspan, rowspan)
