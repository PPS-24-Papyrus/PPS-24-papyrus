package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.TableStyle
import papyrus.logic.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}

import scala.annotation.targetName
import scala.collection.mutable.ArrayBuffer


//class TableBuilder:
//  private var caption: Option[String] = None
//  private var rows: List[RowBuilder] = List.empty
//  var backgroundColor: ColorString = DefaultValues.backgroundColorTable
//  var margin: Margin = DefaultValues.marginTable
//  var textAlign: Alignment = DefaultValues.textAlignTable
//  var width: Width = DefaultValues.widthTable
//  var alignment: Align = DefaultValues.alignTable
//
//
//  def withCaption(caption: String): Unit =
//    this.caption = Some(caption)
//
//  def addRow(row: RowBuilder): Unit =
//    rows = rows :+ row
//
//  def build(): Table = Table(caption, rows.map(_.build()), TableStyle(backgroundColor, margin, textAlign, width, alignment))
//

case class TableBuilder(
                     private val caption: Option[String] = None,
                     private val rows: ArrayBuffer[RowBuilder] = ArrayBuffer.empty[RowBuilder],
                     private val backgroundColor: ColorString = DefaultValues.backgroundColorTable,
                     private val margin: Margin = DefaultValues.marginTable,
                     private val textAlign: Alignment = DefaultValues.textAlignTable,
                     private val width: Width = DefaultValues.widthTable,
                     private val alignment: Align = DefaultValues.alignTable
                   ):

  def withCaption(caption: String): TableBuilder = this.copy(caption = Some(caption))
  def withBackgroundColor(color: ColorString): TableBuilder = this.copy(backgroundColor = color)


  def addRow(row: RowBuilder): TableBuilder =
    rows += row
    this

  def build(): Table =
    Table(caption, rows.map(_.build()).toList, TableStyle(backgroundColor, margin, textAlign, width, alignment))

case class RowBuilder(val cells: ArrayBuffer[CellBuilder] = ArrayBuffer.empty[CellBuilder]):
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

  def build(): Row = Row(cells.map(_.build()).toList)

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
                        val content: String = "",
                        private val head: Boolean = false,
                        private val colspan: Int = 1,
                        private val rowspan: Int = 1
                      ):
  def withContent(content: String): CellBuilder = this.copy(content = content)
  def asHeader(): CellBuilder = this.copy(head = true)
  def withColspan(colspan: Int): CellBuilder = this.copy(colspan = colspan)
  def withRowspan(rowspan: Int): CellBuilder = this.copy(rowspan = rowspan)

  def build(): Cell = Cell(content, head, colspan, rowspan)
