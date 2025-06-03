package papyrus.logic.layerElement.captionElement

trait  Table extends CaptionElement:
  def rows: List[Row]

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
  def apply(caption: Option[String], rows: List[Row]): Table = new TableImpl(caption, rows)

  private class TableImpl(override val caption: Option[String],
                          override val rows: List[Row]) extends Table:
    override def render: String =
      val bodyRows = rows.map(_.render).mkString
      val captionString = caption.map(c => s"<caption>$c</caption>").getOrElse("")
      s"<table>$captionString<tbody>$bodyRows</tbody></table>"

    override def renderStyle: String =
      """
        |table {
        |  width: auto;
        |  border-collapse: collapse;
        |  margin: 20px 0;
        |}
        |
        |th, td {
        |  border: 1px solid #ddd;
        |  padding: 8px;
        |}
        |
        |th {
        |  background-color: #f2f2f2;
        |  text-align: left;
        |}
      """.stripMargin

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