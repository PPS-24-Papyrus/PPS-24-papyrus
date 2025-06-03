package papyrus.logic.builders

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import io.github.iltotore.iron.autoRefine


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

  def build(): Row = Row(cells.map(_.build()))

class CellBuilder:
  private var content: String = ""
  private var head: Boolean = false
  private var colspan: Int = 1
  private var rowspan: Int = 1

  def withContent(content: String): this.type =
    this.content = content
    this

  def asHeader(): Unit =
    this.head = true

  def withColspan(colspan: Int): Unit =
    this.colspan = colspan

  def withRowspan(rowspan: Int): Unit =
    this.rowspan = rowspan

  def build(): Cell = Cell(content, head, colspan, rowspan)
