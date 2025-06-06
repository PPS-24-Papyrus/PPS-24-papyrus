package papyrus.logic.layerElement.captionElement

import papyrus.logic.styleObjects.TableStyle

trait  Table extends CaptionElement:
  def rows: List[Row]
  def tableStyle: TableStyle

trait Row:
  def cells: List[Cell]
  def render: String

trait Cell:
  def content: String
  def head: Boolean
  def colspan: Int
  def rowspan: Int
  def render: String

object Table:
  def apply(caption: Option[String], rows: List[Row], tableStyle: TableStyle): Table = new TableImpl(caption, rows, tableStyle)

  private class TableImpl(override val caption: Option[String],
                          override val rows: List[Row],
                          override val tableStyle: TableStyle) extends Table:
    override def render: String =
      val bodyRows = rows.map(_.render).mkString
      val captionString = caption.map(c => s"<caption>$c</caption>").getOrElse("")
      s"""<div class="${tableStyle.tag}"><table>$captionString<tbody>$bodyRows</tbody></table></div>"""

    override def renderStyle: String =
      tableStyle.renderStyle

object Row:
  def apply(cells: List[Cell]): Row = RowImpl(cells)

  private class RowImpl(override val cells: List[Cell]) extends Row:
    override def render: String =
      val cellStrings = cells.map(_.render).mkString
      s"<tr>$cellStrings</tr>"

object Cell:
  def apply(content: String, head: Boolean = false, colspan: Int = 1, rowspan: Int = 1): Cell = CellImpl(content, head, colspan, rowspan)

  private class CellImpl(override val content: String,
                         override val head: Boolean,
                         override val colspan: Int,
                         override val rowspan: Int) extends Cell:
    override def render: String =
      val tag = if head then "th" else "td"
      s"<$tag colspan='$colspan' rowspan='$rowspan'>$content</$tag>"