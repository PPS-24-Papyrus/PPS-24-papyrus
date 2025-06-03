package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import io.github.iltotore.iron.autoRefine

import scala.annotation.targetName


class TableBuilder:
  private var caption: Option[String] = None
  private var rows: List[RowBuilder] = List.empty

  def withCaption(caption: String): Unit =
    this.caption = Some(caption)

  def addRow(row: RowBuilder): Unit =
    rows = rows :+ row

  def build(): Table = Table(caption, rows.map(_.build()))


class RowBuilder:
  private var cells: List[CellBuilder] = List.empty

  def addCell(cell: CellBuilder): this.type =
    cells = cells :+ cell
    this

  def |(cell: String): this.type =
    addCell(new CellBuilder().withContent(cell))

  def hsh(cell: String): this.type =
    addCell(new CellBuilder().withContent(cell).asHeader())

  def s(cell: String): this.type =
    |(cell)

  def |-(cell: String): this.type =
    addCell(new CellBuilder().withContent(cell).withColspan(2))

  def |^(cell: String): this.type =
    addCell(new CellBuilder().withContent(cell).withRowspan(2))

  def build(): Row = Row(cells.map(_.build()))

class CellBuilder:
  private var content: String = ""
  private var head: Boolean = false
  private var colspan: Int = 1
  private var rowspan: Int = 1

  def withContent(content: String): this.type =
    this.content = content
    this

  def asHeader(): this.type =
    this.head = true
    this

  def withColspan(colspan: Int): this.type =
    this.colspan = colspan
    this

  def withRowspan(rowspan: Int): this.type =
    this.rowspan = rowspan
    this

  def build(): Cell = Cell(content, head, colspan, rowspan)
