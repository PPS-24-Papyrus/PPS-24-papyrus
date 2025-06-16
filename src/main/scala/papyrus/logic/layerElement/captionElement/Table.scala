package papyrus.logic.layerElement.captionElement

import papyrus.logic.Renderer.Text.*
import papyrus.logic.styleObjects.TableStyle

trait  Table[T] extends CaptionElement:
  def rows: List[Row[T]]
  def tableStyle: TableStyle

trait Row[T]:
  def cells: List[Cell[T]]
  def render: MainText

trait Cell[T]:
  def content: T
  def head: Boolean
  def colspan: Int
  def rowspan: Int
  def render: MainText

object Table:
  def apply[T](caption: Option[String], rows: List[Row[T]], tableStyle: TableStyle): Table[T] = new TableImpl(caption, rows, tableStyle)

  private class TableImpl[T](override val caption: Option[String],
                          override val rows: List[Row[T]],
                          override val tableStyle: TableStyle) extends Table[T]:
    override def render: MainText =
      val bodyRows = rows.map(_.render).mkString
      val captionString = caption.map(c => s"<caption>$c</caption>").getOrElse("")
      s"""<div class="${tableStyle.tag}">\n<table>\n$captionString<tbody>\n$bodyRows</tbody>\n</table>\n</div>""".stripMargin.toMainText

    override def renderStyle: StyleText =
      tableStyle.renderStyle

object Row:
  def apply[T](cells: List[Cell[T]]): Row[T] = RowImpl(cells)

  private class RowImpl[T](override val cells: List[Cell[T]]) extends Row[T]:
    override def render: MainText =
      val cellStrings = cells.map(_.render).mkString
      s"<tr>\n$cellStrings</tr>\n".toMainText

object Cell:
  def apply[T](content: T, head: Boolean = false, colspan: Int = 1, rowspan: Int = 1): Cell[T] = CellImpl(content, head, colspan, rowspan)

  private class CellImpl[T](override val content: T,
                         override val head: Boolean,
                         override val colspan: Int,
                         override val rowspan: Int) extends Cell[T]:
    override def render: MainText =
      val tag = if head then "th" else "td"
      s"<$tag colspan='$colspan' rowspan='$rowspan'>$content</$tag>\n".toMainText